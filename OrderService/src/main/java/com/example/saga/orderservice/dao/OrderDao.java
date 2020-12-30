package com.example.saga.orderservice.dao;

import org.apache.ibatis.annotations.*;
import org.openapitools.model.Order;

import java.util.List;

@Mapper
public interface OrderDao {
    @Insert("INSERT INTO ORDERSERVICE.ORDERS (STATUS,COMPLETE,QUANTITY,SHIPDATE) " +
            "VALUES(#{status},#{complete},#{quantity},#{shipDate})")
    public void createOrder(Order Order);

    @Select({"SELECT * FROM ORDERSERVICE.ORDERS"})
    public List<Order> getAllOrders();

    @Select("SELECT * from ORDERSERVICE.ORDERS WHERE ID=#{id}")
    public Order getOrderById(@Param("id") Long id);
}
