package com.example.mongoApp.repositoryLayer;

import com.example.mongoApp.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IUserRepository extends MongoRepository<User,String> {
}
