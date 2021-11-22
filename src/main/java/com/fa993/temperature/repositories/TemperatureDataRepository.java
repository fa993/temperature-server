package com.fa993.temperature.repositories;

import com.fa993.temperature.model.dto.AggregateData;
import com.fa993.temperature.model.dto.Coordinate;
import com.fa993.temperature.model.dto.Metadata;
import com.fa993.temperature.model.entity.TemperatureData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TemperatureDataRepository extends JpaRepository<TemperatureData, Integer> {

    @Query(
            value = "select distinct latitude, longitude from temperature_data",
            nativeQuery = true
    )
    public List<String[]> findDistinctBy();

    @Query(
            value = "select mac_address as macAddress, AVG(temperature) as average, MAX(temperature) as max, MIN(temperature) as min from temperature_data where time >= :lower AND time < :upper group by mac_address",
            nativeQuery = true
    )
    public List<AggregateData> getAllAggregates(@Param(value = "lower") Long lowerBound, @Param(value = "upper") Long upperBound);

    @Query(
            value = "select distinct mac_address as macAddress, latitude, longitude, elevation, size from temperature_data",
            nativeQuery = true
    )
    public List<Metadata> getAllMetadata();

    public List<String> findMacAddressBy();

}
