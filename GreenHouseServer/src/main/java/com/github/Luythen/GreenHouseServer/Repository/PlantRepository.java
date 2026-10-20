package com.github.Luythen.GreenHouseServer.Repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.Luythen.GreenHouseServer.Model.PlantModel;

public interface PlantRepository extends MongoRepository<PlantModel, String> {
    List<PlantModel> findByPlantCrateId (String crateId);
}
