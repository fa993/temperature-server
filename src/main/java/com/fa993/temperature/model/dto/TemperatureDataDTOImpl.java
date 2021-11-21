package com.fa993.temperature.model.dto;

import javax.persistence.Column;

public class TemperatureDataDTOImpl implements TemperatureDataDTO {

    private Integer size;

    private double temperature;

    private String latitude;

    private String longitude;

    private String elevation;

    public TemperatureDataDTOImpl() {
    }

    public TemperatureDataDTOImpl(int size, double temperature, String latitude, String longitude, String elevation) {
        this.size = size;
        this.temperature = temperature;
        this.latitude = latitude;
        this.longitude = longitude;
        this.elevation = elevation;
    }

    @Override
    public Integer getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }
}
