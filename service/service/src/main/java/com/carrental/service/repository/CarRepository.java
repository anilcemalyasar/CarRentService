package com.carrental.service.repository;

import com.carrental.service.model.entity.Car;
import com.carrental.service.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Car findByModel(String model);
    Car findByTypeAndModel(String type, String model);

    List<Car> findByCompanies(Company company);

}