package com.github.Luythen.GreenHouseServer.Model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SoilMoistureModel {

    @Id
    private String id;

    private String moistureSensorId;
    private String greenHouseId;
    private int measurement;
    private Instant time;

    public String getId() {
        return id;
    }

    public String getMoistureSensorId() {
        return moistureSensorId;
    }

    public void setMoistureSensorId(String moistureSensorId) {
        this.moistureSensorId = moistureSensorId;
    }

    public String getGreenHouseId() {
        return greenHouseId;
    }

    public void setGreenHouseId(String greenHouseId) {
        this.greenHouseId = greenHouseId;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public int getMeasurement() {
        return measurement;
    }

    public void setMeasurement(int measurement) {
        this.measurement = measurement;
    }

}
