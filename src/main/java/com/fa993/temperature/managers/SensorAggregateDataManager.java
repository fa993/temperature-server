package com.fa993.temperature.managers;

import com.fa993.temperature.model.entity.SensorAggregateData;
import com.fa993.temperature.repositories.SensorAggregateDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class SensorAggregateDataManager {

    @Autowired
    SensorAggregateDataRepository repo;

    public SensorAggregateData save(SensorAggregateData dt) {
        return repo.saveAndFlush(dt);
    }

    public List<SensorAggregateData> getAll(int dayBeforeNow) {
        Long dayBegin = Instant.now().minus(dayBeforeNow, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).getEpochSecond();
        Long dayEnd = Instant.now().minus(dayBeforeNow - 1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).getEpochSecond();
        return repo.findByTimeGreaterThanEqualAndTimeLessThanEqual(dayBegin, dayEnd);
    }

    public boolean exists(String latitude, String longitude, Long timeLower, Long timeUpper) {
        return repo.existsByLatitudeAndLongitudeAndTimeGreaterThanEqualAndTimeLessThan(latitude, longitude, timeLower, timeUpper);
    }

}
