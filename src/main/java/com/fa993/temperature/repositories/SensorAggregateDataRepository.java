package com.fa993.temperature.repositories;

import com.fa993.temperature.model.entity.SensorAggregateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SensorAggregateDataRepository extends JpaRepository<SensorAggregateData, Integer> {

    public List<SensorAggregateData> findByTimeGreaterThanEqualAndTimeLessThanEqual(long time1, long time2);

    public boolean existsByLatitudeAndLongitudeAndTimeGreaterThanEqualAndTimeLessThan(String latitude, String longitude, Long timeLower, Long timeUpper);

}
