package com.carrental.service.business.impl;

import com.carrental.service.business.OrderService;
import com.carrental.service.model.dto.OrderDto;
import com.carrental.service.model.entity.Order;
import com.carrental.service.repository.OrderRepository;
import com.carrental.service.util.mapper.ModelMapperManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ModelMapperManager modelMapperManager;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapperManager modelMapperManager) {
        this.orderRepository = orderRepository;
        this.modelMapperManager = modelMapperManager;
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapperManager.forResponse().map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Order getOrderById(Long orderId) {
        if(!existsById(orderId)) {
            String errorMessage = orderId + " numaralı bir kiralama işlemi bulunmamaktadır!";
            logger.error(errorMessage);
        }
        Order order = orderRepository.findById(orderId).get();
        return order;
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findAll()
                .stream()
                .filter(order -> order.getCustomer().getId() == customerId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrdersByCarId(Long carId) {
        return orderRepository.findAll()
                .stream()
                .filter(order -> order.getCar().getId() == carId)
                .collect(Collectors.toList());
    }

    @Override
    public String addNewOrder(Order order) {
        orderRepository.save(order);
        return order.getId() + " numaralı kiralama işlemi sisteme eklendi!";
    }

    @Override
    public String deleteById(Long orderId) {
        return null;
    }

    @Override
    public boolean existsById(Long orderId) {
        Optional<Order> optional = orderRepository.findById(orderId);
        return optional.isPresent();
    }
}