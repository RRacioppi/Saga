package com.example.saga.orderservice.controller;

import com.example.saga.orderservice.service.OrderSagaService;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.OrderSagasApi;
import org.openapitools.model.OrderSaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class OrderSagaController implements OrderSagasApi {

    @Autowired
    OrderSagaService orderSagaService;

    @Override
    public ResponseEntity<List<OrderSaga>> getOrderSagas() {
        return ResponseEntity.ok(orderSagaService.getAllOrderSagas());
    }
}