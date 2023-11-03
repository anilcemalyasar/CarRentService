package com.carrental.service.controller;

import com.carrental.service.business.OrderService;
import com.carrental.service.model.dto.OrderDto;
import com.carrental.service.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable("orderId") Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PostMapping("")
    public String addNewOrder(@RequestBody OrderDto orderDto) {
        return orderService.addNewOrder(orderDto);
    }

    @DeleteMapping("/{orderId}")
    public String deleteById(@PathVariable("orderId") Long orderId) {
        return orderService.deleteById(orderId);
    }

    @GetMapping("/customers/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathVariable("customerId") Long customerId) {
        return orderService.getOrdersByCustomerId(customerId);
    }



}