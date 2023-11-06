package com.carrental.service.repository;

import com.carrental.service.model.entity.Car;
import com.carrental.service.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Company c JOIN c.cars car WHERE c.id = :companyId AND car.id = :carId")
    boolean isCarInCompany(@Param("companyId") Long companyId, @Param("carId") Long carId);

}