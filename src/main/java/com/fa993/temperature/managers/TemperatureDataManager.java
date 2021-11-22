package com.fa993.temperature.managers;

import com.fa993.temperature.model.dto.*;
import com.fa993.temperature.model.entity.HistoricalData;
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

    public TemperatureData save(TemperatureData dt){
        return repo.saveAndFlush(dt);
    }

    public List<Coordinate> getLatsAndLong() {
        return repo.findDistinctBy().stream().map(t -> new Coordinate(t[0], t[1])).toList();
    }

    public List<AggregateData> getAggregates(Long lowerBound, Long upperBound) {
        return repo.getAllAggregates(lowerBound, upperBound);
    }

    public List<Metadata> getAllMetadata() {
        return repo.getAllMetadata();
    }

}
