package com.example.saga.orderservice.controller;

import com.example.saga.orderservice.dao.OrderDao;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.OrdersApi;
import org.openapitools.model.Order;
import org.openapitools.model.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@RestController
public class OrderController implements OrdersApi {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private OrderDao orderDao;

    @Override
    public ResponseEntity<Order> postOrder(@Valid OrderData body) {
        Order orderToCreate= new Order();
        orderToCreate.setComplete(false);
        orderToCreate.setStatus(Order.StatusEnum.PLACED);
        orderToCreate.setQuantity(body.getQuantity());
        orderToCreate.setShipDate(body.getShipDate());
        orderDao.createOrder(orderToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderToCreate);
    }

    @Override
    public ResponseEntity<Order> getOrder(Integer id) {
        Order order = orderDao.getOrderById(new Long(id));
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> allOrders = orderDao.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }
}