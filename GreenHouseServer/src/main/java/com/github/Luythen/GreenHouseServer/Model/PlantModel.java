package com.github.Luythen.GreenHouseServer.Model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document 
public class PlantModel {
    @Id
    private String id;
    private String plantType;
    private Instant plantedAt;
    private Instant RemovedAt;
    private Boolean active;
    
    @Indexed
    private String plantCrateId;

    public String getId() {
        return id;
    }

    public String getPlantType() {
        return plantType;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public String getPlantCrateId() {
        return plantCrateId;
    }

    public void setPlantCrateId(String plantCrateId) {
        this.plantCrateId = plantCrateId;
    }

    public Instant getPlantedAt() {
        return plantedAt;
    }

    public void setPlantedAt(Instant plantedAt) {
        this.plantedAt = plantedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getRemovedAt() {
        return RemovedAt;
    }

    public void setRemovedAt(Instant removedAt) {
        RemovedAt = removedAt;
    }
    
}
