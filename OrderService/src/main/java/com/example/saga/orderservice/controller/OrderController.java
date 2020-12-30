package com.example.saga.orderservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.OrdersApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class OrderController implements OrdersApi {

}