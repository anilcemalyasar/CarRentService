package com.carrental.service.business;

import com.carrental.service.model.dto.CarDto;
import com.carrental.service.model.dto.CompanyDto;
import com.carrental.service.model.entity.Car;
import com.carrental.service.model.entity.Company;
import com.carrental.service.model.vm.PurchaseCarToCompanyVm;

import java.util.List;

public interface CompanyService {

    List<CompanyDto> getAllCompanies();
    CompanyDto getCompanyById(Long companyId);
    String addNewCompany(Company company);
    String deleteCompanyById(Long companyId);
    List<CarDto> getRentalCarsOfCompany(Long companyId);
    String purchaseCarToCompany(PurchaseCarToCompanyVm carToCompanyVm);

}