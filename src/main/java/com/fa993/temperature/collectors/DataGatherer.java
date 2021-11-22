package com.fa993.temperature.collectors;

import com.fa993.temperature.managers.HistoricalDataManager;
import com.fa993.temperature.managers.SensorAggregateDataManager;
import com.fa993.temperature.managers.TemperatureDataManager;
import com.fa993.temperature.model.dto.AggregateData;
import com.fa993.temperature.model.dto.Coordinate;
import com.fa993.temperature.model.dto.Metadata;
import com.fa993.temperature.model.entity.HistoricalData;
import com.fa993.temperature.model.entity.SensorAggregateData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DataGatherer {

    @Value("${weather.api-key}")
    String key;

    @Autowired
    TemperatureDataManager temperatureDataManager;

    @Autowired
    HistoricalDataManager historicalDataManager;

    @Autowired
    SensorAggregateDataManager sensorAggregateDataManager;

    RestTemplate restTemplate = new RestTemplate();

    ObjectMapper mapper = new ObjectMapper();

//    @Scheduled(cron = "")
    public void gatherHistoric() {
        gatherHistoric(1);
    }

    public void gatherHistoric(int day) {
        List<Coordinate> cds = temperatureDataManager.getLatsAndLong();
        Set<Coordinate> unique = new HashSet<>(cds);
        long ts = Instant.now().minus(day, ChronoUnit.DAYS).getEpochSecond();
        unique.forEach(t -> {
            //first check if data already exists for that day
            Long dayBegin = Instant.now().minus(day, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).getEpochSecond();
            Long dayEnd = Instant.now().minus(day - 1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).getEpochSecond();
            if(historicalDataManager.exists(t.getLatitude(), t.getLongitude(), dayBegin, dayEnd)){
                return;
            }
            try {
                String fooResourceUrl
                        = "https://api.openweathermap.org/data/2.5/onecall/timemachine?units=metric&lat=" + t.getLatitude() + "&lon=" + t.getLongitude() + "&dt=" + ts + "&appid=" + key;
                ResponseEntity<String> response
                        = restTemplate.getForEntity(fooResourceUrl, String.class);
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode array = root.path("hourly");
                Iterator<JsonNode> eles = array.elements();
                HistoricalData dt = new HistoricalData();
                dt.setLatitude(t.getLatitude());
                dt.setLongitude(t.getLongitude());
                //TODO
                dt.setTime(ts);
                double avgTemp = 0.0;
                double no = 0.0;
                double maxTemp = Double.MIN_VALUE;
                double minTemp = Double.MAX_VALUE;
                while(eles.hasNext()) {
                    JsonNode nd = eles.next();
                    double temp = nd.get("temp").asDouble();
                    avgTemp += temp;
                    no+= 1.0;
                    if(temp < minTemp) {
                        minTemp = temp;
                    }
                    if(temp > maxTemp) {
                        maxTemp = temp;
                    }
                }
                dt.setAvgTemp(avgTemp / no);
                dt.setMaxTemp(maxTemp);
                dt.setMinTemp(minTemp);
                historicalDataManager.insert(dt);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

//    @Scheduled(cron = "")
    public void aggregateFromSensors() {
        //figure out last UTC Day start and day END in seconds
        aggregateFromSensors(1);
    }

    public void aggregateFromSensors(int day) {
        Long dayBegin = Instant.now().minus(day, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).getEpochSecond();
        Long dayEnd = Instant.now().minus(day - 1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).getEpochSecond();
        List<Metadata> mdt = temperatureDataManager.getAllMetadata();
        List<AggregateData> dataList = temperatureDataManager.getAggregates(dayBegin, dayEnd);
        dataList.stream().forEach(t -> {
            Metadata dt = mdt.stream().filter(x -> t.getMacAddress().equals(x.getMacAddress())).findFirst().get();
            if(sensorAggregateDataManager.exists(dt.getLatitude(), dt.getLongitude(), dayBegin, dayEnd)){
                return;
            }
            SensorAggregateData sdt = new SensorAggregateData();
            sdt.setMacAddress(t.getMacAddress());
            sdt.setAvgTemp(t.getAverage());
            sdt.setMaxTemp(t.getMax());
            sdt.setMinTemp(t.getMin());
            sdt.setTime(dayBegin);
            sdt.setLatitude(dt.getLatitude());
            sdt.setLongitude(dt.getLongitude());
            sdt.setElevation(dt.getElevation());
            sdt.setSize(dt.getSize());
            sensorAggregateDataManager.save(sdt);
        });
    }

    private double dist(Coordinate c1, Coordinate c2) {
        Double d1 = Double.valueOf(c1.getLatitude());
        Double d2 = Double.valueOf(c2.getLatitude());
        Double d3 = Double.valueOf(c1.getLongitude());
        Double d4 = Double.valueOf(c2.getLongitude());
        return Math.sqrt((d2 - d1) * (d2 - d1) + (d3 - d4) * (d3 - d4));
    }

}
