package com.fa993.temperature.managers;

import com.fa993.temperature.model.dto.TemperatureDataDTO;
import com.fa993.temperature.model.dto.TemperatureDataDTOImpl;
import com.fa993.temperature.model.entity.TemperatureData;
import com.fa993.temperature.repositories.TemperatureDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TemperatureDataManager {

    @Autowired
    TemperatureDataRepository repo;

    @Value("${weather.api-key}")
    String key;

    private long lastRunTime = 0;

    Map<double[], TemperatureDataDTO> responses = new HashMap<>();

    RestTemplate restTemplate = new RestTemplate();

    ObjectMapper mapper = new ObjectMapper();

    public TemperatureData save(TemperatureData dt){
        return repo.saveAndFlush(dt);
    }

    public List<TemperatureDataDTO> getAll() {
        List<TemperatureDataDTO> xl = repo.getAllRecents();
        List<TemperatureDataDTO> rets = new ArrayList<>(xl);
        List<double[]> app = new ArrayList<>();
        xl.forEach(t -> {
            responses.entrySet().stream().filter(s -> dist(s.getKey()[0], s.getKey()[1], Double.parseDouble(t.getLongitude()), Double.parseDouble(t.getLatitude())) < 10).findAny().ifPresentOrElse(f -> rets.add(f.getValue()), ()->{
                app.add(new double[] {Double.parseDouble(t.getLongitude()), Double.parseDouble(t.getLatitude())});
            });
        });
        app.forEach(t -> {
            if(System.currentTimeMillis() - lastRunTime >= 10 * 60) {
                try {
                    String fooResourceUrl
                            = "http://api.openweathermap.org/data/2.5/weather?units=metric&" + "lat=" + t[1] + "&lon=" + t[0] + "&appid=" + key;
                    ResponseEntity<String> response
                            = restTemplate.getForEntity(fooResourceUrl, String.class);
                    JsonNode root = mapper.readTree(response.getBody());
                    //process
                    Double temp = root.path("main").get("temp").asDouble();
                    TemperatureDataDTO tx = new TemperatureDataDTOImpl(40, temp, String.valueOf(t[1]), String.valueOf(t[0]), "0");
                    responses.putIfAbsent(t, tx);
                    rets.add(tx);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return rets;
    }

    private double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1)  + (y2 - y1) * (y2 - y1));
    }

}
