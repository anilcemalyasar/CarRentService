package com.carrental.service.business.impl;

import com.carrental.service.business.CustomerService;
import com.carrental.service.exceptions.CustomerNotFoundException;
import com.carrental.service.model.entity.Customer;
import com.carrental.service.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        if(!existsById(id)) {
            logger.error(id + " numaralı bir müşteri bulunmamaktadır!");
            return null;
        }
        return customerRepository.findById(id).get();
    }

    @Override
    public String addNewCustomer(Customer customer) {
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