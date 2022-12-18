package com.example.mongoApp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@AllArgsConstructor
@Document("order")
public class Order {
    @Id
    private String id;

    private List<String> items;
    private String clientId;
}
