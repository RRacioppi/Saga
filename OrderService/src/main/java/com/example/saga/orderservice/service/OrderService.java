package com.example.saga.orderservice.service;

import com.example.saga.orderservice.dao.OrderDao;
import com.example.saga.orderservice.dao.OrderSagaDao;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Order;
import org.openapitools.model.OrderData;
import org.openapitools.model.OrderSaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderSagaDao orderSagaDao;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Transactional
    public Order createOrder(OrderData orderData) {
        Order order = new Order();
        order.setQuantity(orderData.getQuantity());
        order.setShipDate(orderData.getShipDate());
        order.setComplete(false);
        order.setStatus(Order.StatusEnum.PLACED);
        Long orderId = orderDao.createOrder(order);
        orderSagaDao.createSaga(new OrderSaga().orderId(orderId).status("CREATED"));
        return order;
    }

    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public Order getOrderById(Long id) {
        return orderDao.getOrderById(id);
    }
}
