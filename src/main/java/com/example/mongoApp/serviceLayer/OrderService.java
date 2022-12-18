package com.example.mongoApp.serviceLayer;

import com.example.mongoApp.DTOs.OrderDTO;
import com.example.mongoApp.controllers.handlers.exceptions.model.ResourceNotFoundException;
import com.example.mongoApp.entities.Order;
import com.example.mongoApp.repositoryLayer.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final IOrderRepository _orderRepository;
    @Autowired
    public OrderService (IOrderRepository _orderRepository) {
        this._orderRepository = _orderRepository;
    }
    public List<OrderDTO> getOrders() {
        List<Order> orders = this._orderRepository.findAll();
        return orders.stream()
                .map( (order) -> new OrderDTO(
                        order.getId(),
                        order.getItems(),
                        order.getClientId()
                ))
                .collect(Collectors.toList());
    }
    public OrderDTO getOrderByID(String id) throws ResourceNotFoundException {
        Optional<Order> order = _orderRepository.findById(id);
        if( order.isPresent()){
            Order myOrder = order.get();
            return new OrderDTO(
                    myOrder.getId(),
                    myOrder.getItems(),
                    myOrder.getClientId()
            );
        }
        else {
            throw new ResourceNotFoundException("Order with id : " + id + "was not found");
        }
    }

    public String insertOrder(OrderDTO orderDTO) {
        Order order = new Order(
                orderDTO.getId(),
                orderDTO.getItems(),
                orderDTO.getClientId()
        );
        Order insertedOrder = this._orderRepository.save(order);
        return insertedOrder.getId();
    }

    public String updateOrder(String id, Map<Object, Object> fields) {
        Optional<Order> order = _orderRepository.findById(id);
        if( order.isPresent() ) {
            fields.forEach( (key, value) -> {
                Field field = ReflectionUtils.findField(Order.class, (String) key);
                assert field != null;
                field.setAccessible(true);
                ReflectionUtils.setField(field, order.get(), value);
            });
            Order savedOrder = _orderRepository.save(order.get());
            return savedOrder.getId();
        }
        else {
            throw new ResourceNotFoundException("Order with id : " + id + "was not found");
        }
    }

    public void deleteOrderById(String id) {
        _orderRepository.deleteById(id);
    }

}
