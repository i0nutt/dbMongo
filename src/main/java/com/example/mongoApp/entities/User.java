package com.example.mongoApp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@Document("User")
public class User {
    @Id
    private String id;

    private int role;
    private String username;
    private String password;
}
