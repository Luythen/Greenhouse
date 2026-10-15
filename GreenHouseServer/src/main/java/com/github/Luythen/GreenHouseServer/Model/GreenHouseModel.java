package com.github.Luythen.GreenHouseServer.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document 
public class GreenHouseModel {
    @Id
    private String id;
    private String name;
    private String ownerId;
    
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    
    }
    public void setName(String name) {
        this.name = name;
    
    }
    public String getOwnerId() {
        return ownerId;
    
    }
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
    
}
