package com.example.mongoApp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@Document("item")
public class Item {
    @Id
    private String id;

    private String name;
    private int quantity;
    private String category;
}
