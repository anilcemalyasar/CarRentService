package com.carrental.service.business.impl;

import com.carrental.service.business.CustomerService;
import com.carrental.service.exceptions.CustomerNotFoundException;
import com.carrental.service.model.dto.CustomerDto;
import com.carrental.service.model.entity.Customer;
import com.carrental.service.repository.CustomerRepository;
import com.carrental.service.util.mapper.ModelMapperManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private ModelMapperManager modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               ModelMapperManager modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> modelMapper.forResponse().map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        if(!existsById(id)) {
            String message = id + " numaralı bir müşteri bulunmamaktadır!";
            logger.error(message);
            throw new CustomerNotFoundException(message);
        }
        Customer customer = customerRepository.findById(id).get();
        return modelMapper.forResponse().map(customer, CustomerDto.class);
    }

    @Override
    public String addNewCustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.forRequest().map(customerDto, Customer.class);
        customerRepository.save(customer);
        return customer.getId() + " ID numaralı müşteri sisteme eklendi!";
    }
    @Override
    public String deleteById(Long id) {
        if(!existsById(id)) {
            logger.error(id + " numaralı bir müşteri bulunmamaktadır!");
            return null;
        }
        customerRepository.deleteById(id);
        return id + " numaralı müşteri sistemden silindi!";
    }

    @Override
    public String deleteByFirstName(String firstName) {
        Optional<Customer> optional = customerRepository.findByFirstNameIgnoreCase(firstName);
        if(optional.isEmpty()) {
            String errorMessage = firstName + " adlı bir müşteri bulunmamaktadır!";
            logger.error(errorMessage);
            throw new CustomerNotFoundException(errorMessage);
        }
        Customer customer = optional.get();
        customerRepository.delete(customer);
        return customer.getFirstName() + " " + customer.getLastName()
                + " adlı müşteri sistemden silindi!";
    }

    @Override
    public String updateWalletById(Long id, double deposit) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        double prevWallet = customer.getWallet();
        customer.setWallet(prevWallet + deposit);
        customerRepository.save(customer);
        return id + " numaralı müşterinin cüzdanı " +
                    prevWallet + " TL'den " + customer.getWallet() + " TL'ye güncellendi!";

    }

    @Override
    public String updateMailAddress(Long id, String newAddress) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        customer.setMailAddress(newAddress);
        customerRepository.save(customer);
        return id + " numaralı müşterinin mail adresi " +
                    newAddress + " olarak güncellendi!";
    }

    @Override
    public boolean existsById(Long id) {
        Optional<Customer> optional = customerRepository.findById(id);
        return optional.isPresent();
    }
}