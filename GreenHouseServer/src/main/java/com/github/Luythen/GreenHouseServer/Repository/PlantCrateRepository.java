package com.github.Luythen.GreenHouseServer.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.Luythen.GreenHouseServer.Model.PlantCrateModel;
import java.util.List;


public interface PlantCrateRepository extends MongoRepository<PlantCrateModel, String>{
    List<PlantCrateModel> findByGreenHouseId(String greenHouseId);
}
