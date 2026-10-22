package com.github.Luythen.GreenHouseServer.Repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.Luythen.GreenHouseServer.Model.SoilMoistureModel;

public interface SoilMoistureRepository extends MongoRepository<SoilMoistureModel, String> {
    List<SoilMoistureModel> findAllTopByGreenHouseIdOrderByTimeDesc(String greenHouseId);
}
