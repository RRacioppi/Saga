package com.example.saga.orderservice.dao;

import org.apache.ibatis.annotations.*;
import org.openapitools.model.Order;
import org.openapitools.model.OrderSaga;

import java.util.List;

@Mapper
public interface OrderSagaDao {
    @Insert("INSERT INTO ORDERSERVICE.ORDERSAGAS (ORDER_ID,STATUS) " +
            "VALUES(#{orderId},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public void createSaga(OrderSaga OrderSaga);

    @Select({"SELECT ID, ORDER_ID as ORDERID, STATUS FROM ORDERSERVICE.ORDERSAGAS"})
    public List<OrderSaga> getAllSaga();
}
