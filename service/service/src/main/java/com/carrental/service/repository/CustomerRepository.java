package com.carrental.service.repository;

import com.carrental.service.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByMailAddress(String mailAddress);
    Optional<Customer> findByFirstNameIgnoreCase(String firstName);
}