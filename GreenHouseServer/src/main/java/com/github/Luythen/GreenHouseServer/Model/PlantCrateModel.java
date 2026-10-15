package com.github.Luythen.GreenHouseServer.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document 
public class PlantCrateModel {
    @Id 
    private String id;
    private String label;

    @Indexed
    private String greenHouseId;

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGreenHouseId() {
        return greenHouseId;
    }

    public void setGreenHouseId(String greenHouseId) {
        this.greenHouseId = greenHouseId;
    }

}
