package com.example.mongoApp.controllers;

import com.example.mongoApp.DTOs.OrderDTO;
import com.example.mongoApp.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.example.mongoApp.serviceLayer.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/order")
@ComponentScan({"controllers"})
public class OrderController {
    private final OrderService _orderService;

    /**
     * Injects a service into the controller
     * @param _orderService The service to be injected
     */
    @Autowired
    public OrderController( OrderService _orderService) {
        this._orderService = _orderService;
    }

    /**
     * Retrieves all orders
     * @return The Requested HTML Response
     */
    @GetMapping()
    public ResponseEntity<List<OrderDTO>> getOrders(){
        List<OrderDTO> orders = _orderService.getOrders();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    /**
     * Handles POST for inserting a order
     * @param order The order to be inserted
     * @return The Requested HTML response
     */
    @PostMapping()
    public ResponseEntity<String> insertOrder( @Valid @RequestBody OrderDTO order ) {
        String orderId = _orderService.insertOrder(order);
        return new ResponseEntity<>( orderId, HttpStatus.CREATED );
    }

    /**
     * Retrieves a order
     * @param orderId The id of the order to be retrieved
     * @return The Requested HTML response
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable( "id" ) String orderId) {
        try {
            OrderDTO order = _orderService.getOrderByID( orderId );
            return new ResponseEntity<>( order, HttpStatus.OK );
        } catch (ResourceNotFoundException e ) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Updates a order
     * @param id The id of the order to be updated
     * @param fields The fields of the order to be updated received in JSON format
     * @return The Requested HTML response
     */
    @PatchMapping (value = "/{id}")
    public ResponseEntity<String> updateOrder( @PathVariable String id, @Valid @RequestBody Map<Object, Object> fields) {
        try {
            String updatedId = _orderService.updateOrder(id, fields);
            return new ResponseEntity<>(updatedId, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
    }

    /**
     * Deletes a order given the id
     * @param orderId The id of the order to be deleted
     * @return The Requested HTML response
     */
    @DeleteMapping (value = "/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable( "id" ) String orderId) {
        try {
            _orderService.deleteOrderById( orderId );
            return new ResponseEntity<>( HttpStatus.OK );
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
    }
}