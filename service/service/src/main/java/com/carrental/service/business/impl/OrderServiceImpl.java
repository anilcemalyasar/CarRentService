package com.carrental.service.business.impl;

import com.carrental.service.business.OrderService;
import com.carrental.service.exceptions.CarNotFoundException;
import com.carrental.service.exceptions.CustomerNotFoundException;
import com.carrental.service.model.dto.OrderDto;
import com.carrental.service.model.entity.Car;
import com.carrental.service.model.entity.Customer;
import com.carrental.service.model.entity.Order;
import com.carrental.service.repository.CarRepository;
import com.carrental.service.repository.CustomerRepository;
import com.carrental.service.repository.OrderRepository;
import com.carrental.service.util.mapper.ModelMapperManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private CarRepository carRepository;
    private ModelMapperManager modelMapperManager;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ModelMapperManager modelMapperManager,
                            CustomerRepository customerRepository, CarRepository carRepository) {
        this.orderRepository = orderRepository;
        this.modelMapperManager = modelMapperManager;
        this.customerRepository = customerRepository;
        this.carRepository = carRepository;
    }

    @Override
    @Cacheable(value = "orders")
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapperManager.forResponse().map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "orders", key = "#orderId")
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
    @Transactional
    public String addNewOrder(OrderDto orderDto) {
        Car car = carRepository.findById(orderDto.getCarId()).get();
        /*
        if(!car.getIsAvailable()) {
            String message = car.getType() + " " + car.getModel() + " model araba çoktan başkası tarafından kiralanmıştır!";
            logger.error(message);
            throw new CarNotFoundException("Araba çoktan kiralanmış durumda!");
        }
        */
        if(!customerRepository.existsById(orderDto.getCustomerId())) {
            String message = orderDto.getCustomerId() + " ID numaralı bir müşteri bulunmamaktadır!";
            logger.error(message);
            throw new CustomerNotFoundException(message);
        }
        Customer customer = customerRepository.findById(orderDto.getCustomerId()).get();
        if(!(customer.getWallet() > car.getRentalFee())) {
            throw new CustomerNotFoundException("Müşterinin bütçesi bu arabayı kiralamak için yetersiz");
        }
        Order order = modelMapperManager.forRequest().map(orderDto, Order.class);
        customer.setWallet(customer.getWallet() - car.getRentalFee());
        car.setAvailable(false);
        carRepository.save(car);
        customerRepository.save(customer);
        orderRepository.save(order);
        return order.getId() + " numaralı kiralama işlemi sisteme eklendi!";
    }

    @Override
    @Transactional
    public String deleteById(Long orderId) {
        if(!existsById(orderId)){
            String errorMessage = orderId + " numaralı bir kiralama işlemi bulunmamaktadır!";
            logger.error(errorMessage);
        }

        Order order = orderRepository.findById(orderId).get();
        // After cancelling give rental fee back to customer
        Customer customer = order.getCustomer();
        Car car = order.getCar();
        customer.setWallet(customer.getWallet() + car.getRentalFee());

        customerRepository.save(customer);
        orderRepository.deleteById(orderId);
        return orderId + " numaralı kiralama işlemi sistemden silindi!";
    }

    @Override
    public boolean existsById(Long orderId) {
        Optional<Order> optional = orderRepository.findById(orderId);
        return optional.isPresent();
    }
}