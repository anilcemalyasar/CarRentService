package com.carrental.service.business;

import com.carrental.service.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    String addNewCustomer(Customer customer);
    String deleteById(Long id);
    String updateWalletById(Long id, double deposit);
    String updateMailAddress(Long id, String newAddress);
    boolean existsById(Long id);
}