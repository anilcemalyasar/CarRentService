package com.carrental.service.controller;

import com.carrental.service.business.CompanyService;
import com.carrental.service.model.dto.CarDto;
import com.carrental.service.model.dto.CompanyDto;
import com.carrental.service.model.entity.Car;
import com.carrental.service.model.entity.Company;
import com.carrental.service.model.vm.PurchaseCarToCompanyVm;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("")
    public List<CompanyDto> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{companyId}")
    public CompanyDto getCompanyById(@PathVariable("companyId") Long companyId) {
        return companyService.getCompanyById(companyId);
    }

    @PostMapping("")
    public String addNewCompany(@RequestBody Company company) {
        return companyService.addNewCompany(company);
    }

    @GetMapping("/{companyId}/cars")
    public List<CarDto> getRentalCarsOfCompany(@PathVariable("companyId") Long companyId) {
        return companyService.getRentalCarsOfCompany(companyId);
    }

    @PostMapping("/cars/purchase")
    public String purchaseCarToCompany(@Valid @RequestBody PurchaseCarToCompanyVm carToCompanyVm) {
        return companyService.purchaseCarToCompany(carToCompanyVm);
    }
}