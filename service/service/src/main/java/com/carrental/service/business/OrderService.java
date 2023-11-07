package com.carrental.service.business;

import com.carrental.service.model.dto.OrderDto;
import com.carrental.service.model.entity.Customer;
import com.carrental.service.model.entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAllOrders();
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getOrdersByCustomerId(Long customerId);
    List<OrderDto> getOrdersByCarId(Long carId);
    String addNewOrder(OrderDto orderDto);
    String deleteById(Long orderId);
    boolean existsById(Long orderId);

    OrderDto mapOrderToOrderDto(Order order);

}