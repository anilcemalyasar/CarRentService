package com.carrental.service.business;

import com.carrental.service.model.dto.CustomerDto;
import com.carrental.service.model.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getAllCustomers();
    CustomerDto getCustomerById(Long id);
    String addNewCustomer(CustomerDto customerDto);
    String deleteById(Long id);
    String deleteByFirstName(String firstName);
    String updateWalletById(Long id, double deposit);
    String updateMailAddress(Long id, String newAddress);
    boolean existsById(Long id);
}