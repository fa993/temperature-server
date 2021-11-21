package com.fa993.temperature.repositories;

import com.fa993.temperature.model.dto.TemperatureDataDTO;
import com.fa993.temperature.model.entity.TemperatureData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TemperatureDataRepository extends JpaRepository<TemperatureData, Integer> {

    @Query(
            value = "select temperature, latitude, longitude, elevation, size from temperature_data, (select mac_address, max(time) as t from temperature_data group by mac_address) recent_temperature where recent_temperature.mac_address = temperature_data.mac_address AND recent_temperature.t = temperature_data.time",
            nativeQuery = true
    )
    public List<TemperatureDataDTO> getAllRecents();

}
