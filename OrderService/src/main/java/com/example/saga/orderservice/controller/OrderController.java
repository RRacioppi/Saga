package com.example.saga.orderservice.controller;

import com.example.saga.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.OrdersApi;
import org.openapitools.model.Order;
import org.openapitools.model.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class OrderController implements OrdersApi {

    @Autowired
    OrderService orderService;

    @Override
    public ResponseEntity<Order> postOrder(@Valid OrderData body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(body));
    }

    @Override
    public ResponseEntity<Order> getOrder(Integer id) {
        return ResponseEntity.ok(orderService.getOrderById(new Long(id)));
    }

    @Override
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}