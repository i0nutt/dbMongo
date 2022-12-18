package com.example.mongoApp.repositoryLayer;

import com.example.mongoApp.entities.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IItemRepository extends MongoRepository<Item, String> {
}
