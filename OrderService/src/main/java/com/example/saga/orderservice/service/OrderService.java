package com.example.saga.orderservice.service;

import com.example.saga.orderservice.dao.OrderDao;
import org.openapitools.model.Order;
import org.openapitools.model.OrderData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public Order createOrder(OrderData orderData){
        Order order= new Order();
        order.setQuantity(orderData.getQuantity());
        order.setShipDate(orderData.getShipDate());
        order.setComplete(false);
        order.setStatus(Order.StatusEnum.PLACED);
        orderDao.createOrder(order);
        return order;
    }

    public List<Order> getAllOrders(){
        return orderDao.getAllOrders();
    }

    public Order getOrderById(Long id){
        return orderDao.getOrderById(id);
    }
}
