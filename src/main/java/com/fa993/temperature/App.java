package com.fa993.temperature;

import com.fa993.temperature.collectors.DataGatherer;
import com.fa993.temperature.managers.TemperatureDataManager;
import com.fa993.temperature.model.entity.TemperatureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SpringBootApplication
public class App implements ApplicationRunner {

    @Autowired
    DataGatherer gth;

    @Autowired
    TemperatureDataManager manager;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Put mock data inside
//        mockData();
//        for(int i = 2; i <= 5; i++) {
//            gth.gatherHistoric(i);
//            gth.aggregateFromSensors(i);
//        }

    }

    private void mockData() {
        String[] macAddress = {"c", "d", "e", "f"};
        String[] lats = {"19.376087", "19.376087", "19.093635", "19.093635"};
        String [] longs = {"72.794916", "72.794916", "72.892814", "72.892814"};
        String[] eles = {"20", "40", "20", "40"};

        for(int k = 0; k < macAddress.length; k++) {
            for (int i = 0; i < 5; i++) {
                Long dayBegin = Instant.now().minus(i + 1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).getEpochSecond();
                for (int j = 0; j < 40; j++) {
                    TemperatureData dts = new TemperatureData();
                    dts.setSize(10);
                    dts.setMacAddress(macAddress[k]);
                    dts.setElevation(eles[k]);
                    dts.setLatitude(lats[k]);
                    dts.setLongitude(longs[k]);
                    dts.setTemperature(31 + Math.pow(-1, Math.round(Math.random()) + 1)  * (Math.random() * 2.5));
                    dts.setTime(dayBegin);
                    manager.save(dts);
                }
            }

        }
    }
}
