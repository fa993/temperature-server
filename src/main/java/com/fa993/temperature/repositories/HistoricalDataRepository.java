package com.fa993.temperature.repositories;

import com.fa993.temperature.model.entity.HistoricalData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface HistoricalDataRepository extends JpaRepository<HistoricalData, Integer> {

    public HistoricalData findByLatitudeAndLongitudeAndTimeGreaterThanEqualAndTimeLessThan(String lat, String lon, Long timeLower, Long timeUpper);

    public boolean existsByLatitudeAndLongitudeAndTimeGreaterThanEqualAndTimeLessThan(String latitude, String longitude, Long timeLower, Long timeUpper);
}
