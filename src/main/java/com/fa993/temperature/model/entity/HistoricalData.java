package com.fa993.temperature.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "historical_data")
public class HistoricalData {

    @Id
    @Column(name = "historical_data_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "latitude")
    private String latitude;

    @Column( name="longitude" )
    private String longitude;

    @Column(name = "time")
    private Long time;

    @Column(name = "avg_temp")
    private Double avgTemp;

    @Column(name = "min_temp")
    private Double minTemp;

    @Column(name = "max_temp")
    private Double maxTemp;

    public HistoricalData() {
    }

    public HistoricalData(Integer id, String latitude, String longitude, Long time, Double avgTemp, Double minTemp, Double maxTemp) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.avgTemp = avgTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Double getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(Double avgTemp) {
        this.avgTemp = avgTemp;
    }

    public Double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(Double minTemp) {
        this.minTemp = minTemp;
    }

    public Double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(Double maxTemp) {
        this.maxTemp = maxTemp;
    }
}
