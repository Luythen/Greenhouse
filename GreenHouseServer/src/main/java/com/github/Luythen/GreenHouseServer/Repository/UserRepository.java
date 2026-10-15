package com.github.Luythen.GreenHouseServer.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.Luythen.GreenHouseServer.Model.UserModel;
import java.util.Optional;


public interface UserRepository extends MongoRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String email);
}