package com.example.saga.orderservice.service;

import com.example.saga.orderservice.dao.OrderSagaDao;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.OrderSaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderSagaService {
    @Autowired
    private OrderSagaDao orderSagaDao;

    public List<OrderSaga> getAllOrderSagas() {
        return orderSagaDao.getAllSaga();
    }
}
