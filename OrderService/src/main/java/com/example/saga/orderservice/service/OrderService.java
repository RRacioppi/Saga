package com.example.saga.orderservice.service;

import com.example.saga.orderservice.dao.OrderDao;
import com.example.saga.orderservice.dao.OrderSagaDao;
import com.example.saga.orderservice.messaging.OrderMessaging;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.Order;
import org.openapitools.model.OrderData;
import org.openapitools.model.OrderSaga;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private OrderMessaging orderMessaging;

    @Transactional
    public Order createOrder(OrderData orderData) {
        // CREATE ORDER
        Order order = new Order();
        order.setQuantity(orderData.getQuantity());
        order.setShipDate(orderData.getShipDate());
        order.setComplete(false);
        order.setStatus(Order.StatusEnum.PLACED);
        orderDao.createOrder(order);
        // TODO correct this shit
        // CREATE ORDER SAGA
        OrderSaga orderSaga = new OrderSaga().orderId(order.getId()).status("CREATED");
        orderSagaDao.createSaga(orderSaga);
        // SUBMITTING TO KAFKA
        orderMessaging.sendOrderCreate(order);
        // UPDATE ORDER SAGA
        orderSagaDao.updateSaga(orderSaga.status("SUBMITTED"));
        return order;
    }

    public List<Order> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public Order getOrderById(Long id) {
        return orderDao.getOrderById(id);
    }
}
