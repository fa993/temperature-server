package com.fa993.temperature.model.entity;

import com.fa993.temperature.model.view.JSONView;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
@Table(name = "sensor_aggregate_data")
public class SensorAggregateData {

    @Id
    @Column(name = "sensor_aggregate_data_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "latitude")
    @JsonView(JSONView.NOIDView.class)
    private String latitude;

    @Column( name="longitude" )
    @JsonView(JSONView.NOIDView.class)
    private String longitude;

    @Column(name = "time")
    @JsonView(JSONView.NOIDView.class)
    private Long time;

    @Column(name = "size")
    @JsonView(JSONView.NOIDView.class)
    private int size;

    @Column(name = "avg_temp")
    @JsonView(JSONView.NOIDView.class)
    private Double avgTemp;

    @Column(name = "min_temp")
    @JsonView(JSONView.NOIDView.class)
    private Double minTemp;

    @Column(name = "max_temp")
    @JsonView(JSONView.NOIDView.class)
    private Double maxTemp;

    @Column(name = "elevation")
    @JsonView(JSONView.NOIDView.class)
    private String elevation;

    @Column(name = "mac_address")
    @JsonView(JSONView.NOIDView.class)
    private String macAddress;

    public static SensorAggregateData fromHistoric(HistoricalData hs) {
        SensorAggregateData dt = new SensorAggregateData();
        dt.setSize(40);
        dt.setElevation("-1");
        dt.setLatitude(hs.getLatitude());
        dt.setLongitude(hs.getLongitude());
        dt.setTime(hs.getTime());
        dt.setMinTemp(hs.getMinTemp());
        dt.setMaxTemp(hs.getMaxTemp());
        dt.setAvgTemp(hs.getAvgTemp());
        return dt;
    }

    public SensorAggregateData(Integer id, String latitude, String longitude, Long time, int size, Double avgTemp, Double minTemp, Double maxTemp, String elevation, String macAddress) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.size = size;
        this.avgTemp = avgTemp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.elevation = elevation;
        this.macAddress = macAddress;
    }

    public SensorAggregateData() {
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
