package com.fa993.temperature.api;

import com.fa993.temperature.managers.HistoricalDataManager;
import com.fa993.temperature.managers.SensorAggregateDataManager;
import com.fa993.temperature.managers.TemperatureDataManager;
import com.fa993.temperature.model.entity.SensorAggregateData;
import com.fa993.temperature.model.entity.TemperatureData;
import com.fa993.temperature.model.view.JSONView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WebController {

    @Autowired
    TemperatureDataManager temperatureDataManager;

    @Autowired
    SensorAggregateDataManager sensorAggregateDataManager;

    @Autowired
    HistoricalDataManager historicalDataManager;

    @GetMapping("/temp")
    public void logData(TemperatureData data){
        temperatureDataManager.save(data);
    }

    @GetMapping("/all/{dayBeforeNow}")
    @JsonView(JSONView.NOIDView.class)
    public List<SensorAggregateData> getAll(@PathVariable(name = "dayBeforeNow") int dayBeforeNow) {
        List<SensorAggregateData> dts = sensorAggregateDataManager.getAll(dayBeforeNow);
        dts.addAll(historicalDataManager.getAmbients(dts));
        return dts;
    }

}
