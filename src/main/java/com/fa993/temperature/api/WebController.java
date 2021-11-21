package com.fa993.temperature.api;

import com.fa993.temperature.managers.TemperatureDataManager;
import com.fa993.temperature.model.dto.TemperatureDataDTO;
import com.fa993.temperature.model.entity.TemperatureData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class WebController {

    @Autowired
    TemperatureDataManager manager;

    @GetMapping("/temp")
    public void logData(TemperatureData data){
        manager.save(data);
    }

    @GetMapping("/all")
    public List<TemperatureDataDTO> getAll() {
        return manager.getAll();
    }

}
