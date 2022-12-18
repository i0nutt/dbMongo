package com.example.mongoApp.controllers;

import com.example.mongoApp.DTOs.UserDTO;
import com.example.mongoApp.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.example.mongoApp.serviceLayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
@ComponentScan({"controllers"})
public class UserController {
    private final UserService _userService;

    /**
     * Injects a service into the controller
     * @param _userService The service to be injected
     */
    @Autowired
    public UserController( UserService _userService) {
        this._userService = _userService;
    }

    /**
     * Retrieves all users
     * @return The Requested HTML Response
     */
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<UserDTO> users = _userService.getUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Handles POST for inserting a user
     * @param user The user to be inserted
     * @return The Requested HTML response
     */
    @PostMapping()
    public ResponseEntity<String> insertUser( @Valid @RequestBody UserDTO user ) {
        String userId = _userService.insertUser(user);
        return new ResponseEntity<>( userId, HttpStatus.CREATED );
    }

    /**
     * Retrieves a user
     * @param userId The id of the user to be retrieved
     * @return The Requested HTML response
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable( "id" ) String userId) {
        try {
            UserDTO user = _userService.getUserByID( userId );
            return new ResponseEntity<>( user, HttpStatus.OK );
        } catch (ResourceNotFoundException e ) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Updates a user
     * @param id The id of the user to be updated
     * @param fields The fields of the user to be updated received in JSON format
     * @return The Requested HTML response
     */
    @PatchMapping (value = "/{id}")
    public ResponseEntity<String> updateUser( @PathVariable String id, @Valid @RequestBody Map<Object, Object> fields) {
        try {
            String updatedId = _userService.updateUser(id, fields);
            return new ResponseEntity<>(updatedId, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Deletes a user given the id
     * @param userId The id of the user to be deleted
     * @return The Requested HTML response
     */
    @DeleteMapping (value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable( "id" ) String userId) {
        try {
            _userService.deleteUserById( userId );
            return new ResponseEntity<>( HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
    }
}