package com.example.mongoApp.repositoryLayer;

import com.example.mongoApp.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IOrderRepository extends MongoRepository<Order, String> {
}
