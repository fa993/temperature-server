package com.fa993.temperature.managers;

import com.fa993.temperature.model.entity.HistoricalData;
import com.fa993.temperature.model.entity.SensorAggregateData;
import com.fa993.temperature.repositories.HistoricalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class HistoricalDataManager {

    @Autowired
    HistoricalDataRepository repo;

    public HistoricalData insert(HistoricalData dt) {
        return repo.saveAndFlush(dt);
    }

    public List<SensorAggregateData> getAmbients(List<SensorAggregateData> reference) {

        return reference.stream().map(t -> {
           Instant lower = Instant.ofEpochSecond(t.getTime());
           Long upper = lower.plus(1, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS).getEpochSecond();
            return repo.findByLatitudeAndLongitudeAndTimeGreaterThanEqualAndTimeLessThan(t.getLatitude(), t.getLongitude(), t.getTime(), upper);
        }).map(SensorAggregateData::fromHistoric).toList();
    }

    public boolean exists(String latitude, String longitude, Long timeLower, Long timeUpper) {
        return repo.existsByLatitudeAndLongitudeAndTimeGreaterThanEqualAndTimeLessThan(latitude, longitude, timeLower, timeUpper);
    }

}
