package com.cart.demo.controller;

import java.util.List;

import javax.validation.Valid;

import com.cart.demo.dto.OrderCreateRequest;
import com.cart.demo.entity.Orders;
import com.cart.demo.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by satish on 15/12/18.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createOrder(@Valid @RequestBody OrderCreateRequest orderCreateRequest) {
        orderService.createOrder(orderCreateRequest);
    }

    @GetMapping(value = "all")
    @ResponseStatus(HttpStatus.OK)
    public List<Orders> getorders() {
        return orderService.getAllOrders();
    }


}
