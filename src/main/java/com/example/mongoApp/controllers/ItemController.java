package com.example.mongoApp.controllers;

import com.example.mongoApp.DTOs.ItemDTO;
import com.example.mongoApp.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.example.mongoApp.serviceLayer.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/item")
@ComponentScan({"controllers"})
public class ItemController {
    private final ItemService _itemService;

    /**
     * Injects a service into the controller
     * @param _itemService The service to be injected
     */
    @Autowired
    public ItemController( ItemService _itemService) {
        this._itemService = _itemService;
    }

    /**
     * Retrieves all items
     * @return The Requested HTML Response
     */
    @GetMapping()
    public ResponseEntity<List<ItemDTO>> getItems(){
        List<ItemDTO> items = _itemService.getItems();

        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Handles POST for inserting a item
     * @param item The item to be inserted
     * @return The Requested HTML response
     */
    @PostMapping()
    public ResponseEntity<String> insertItem( @Valid @RequestBody ItemDTO item ) {
        String itemId = _itemService.insertItem(item);
        return new ResponseEntity<>( itemId, HttpStatus.CREATED );
    }

    /**
     * Retrieves an item
     * @param itemId The id of the item to be retrieved
     * @return The Requested HTML response
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemDTO> getItem(@PathVariable( "id" ) String itemId) {
        try {
            ItemDTO item = _itemService.getItemByID( itemId );
            return new ResponseEntity<>( item, HttpStatus.OK );
        } catch (ResourceNotFoundException e ) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Updates a item
     * @param id The id of the item to be updated
     * @param fields The fields of the item to be updated received in JSON format
     * @return The Requested HTML response
     */
    @PatchMapping (value = "/{id}")
    public ResponseEntity<String> updateItem( @PathVariable String id, @Valid @RequestBody Map<Object, Object> fields) {
        try {
            String updatedId = _itemService.updateItem(id, fields);
            return new ResponseEntity<>(updatedId, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Deletes an item given the id
     * @param itemId The id of the item to be deleted
     * @return The Requested HTML response
     */
    @DeleteMapping (value = "/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable( "id" ) String itemId) {
        try {
            _itemService.deleteItemById( itemId );
            return new ResponseEntity<>( HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
    }
}