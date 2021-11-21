package com.fa993.temperature.model.entity;

import com.fa993.temperature.model.dto.TemperatureDataDTO;

import javax.persistence.*;

@Entity
@Table(name = "temperature_data")
public class TemperatureData {

    @Column(name = "temperature_data_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mac_address")
    private String macAddress;

    @Column(name = "time")
    private long time;

    @Column(name = "size")
    private Integer size;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "elevation")
    private String elevation;

    public TemperatureData() {
    }

    public TemperatureData(Integer id, String macAddress, long time, int size, double temperature, String latitude, String longitude, String elevation) {
        this.id = id;
        this.macAddress = macAddress;
        this.time = time;
        this.size = size;
        this.temperature = temperature;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }
}
