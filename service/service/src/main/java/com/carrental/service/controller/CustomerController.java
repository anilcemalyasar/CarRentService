package com.carrental.service.controller;

import com.carrental.service.business.CustomerService;
import com.carrental.service.model.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("")
    public String addNewCustomer(@RequestBody Customer customer) {
        return customerService.addNewCustomer(customer);
    }

    @DeleteMapping("/{customerId}")
    public String deleteById(@PathVariable("customerId") Long customerId) {
        return customerService.deleteById(customerId);
    }

    @PatchMapping("/{customerId}/wallet/deposit/{deposit}")
    public String updateWallet(@PathVariable("customerId") Long customerId,
                               @PathVariable("deposit") double deposit) {
        return customerService.updateWalletById(customerId, deposit);
    }

    @PatchMapping("/{customerId}/mailAddress/update")
    public String updateMailAddress(@PathVariable("customerId") Long customerId,
            @RequestParam("newAddress") String newAddress) {
        return customerService.updateMailAddress(customerId, newAddress);
    }

}