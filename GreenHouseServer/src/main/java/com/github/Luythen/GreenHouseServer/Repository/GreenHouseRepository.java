package com.github.Luythen.GreenHouseServer.Repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.Luythen.GreenHouseServer.Model.GreenHouseModel;



public interface GreenHouseRepository extends MongoRepository<GreenHouseModel, String> {
    Optional<GreenHouseModel> findById(String id);
    Optional<GreenHouseModel> findByOwnerId(String ownerId);
}
