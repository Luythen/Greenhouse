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
    
    @Indexed
    private String plantCrateId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
    
}
