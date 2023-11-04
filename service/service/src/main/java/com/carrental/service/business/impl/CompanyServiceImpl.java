package com.carrental.service.business.impl;

import com.carrental.service.business.CompanyService;
import com.carrental.service.exceptions.CarNotFoundException;
import com.carrental.service.exceptions.CustomerNotFoundException;
import com.carrental.service.model.dto.CarDto;
import com.carrental.service.model.entity.Car;
import com.carrental.service.model.entity.Company;
import com.carrental.service.model.vm.PurchaseCarToCompanyVm;
import com.carrental.service.repository.CarRepository;
import com.carrental.service.repository.CompanyRepository;
import com.carrental.service.util.mapper.ModelMapperManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CarRepository carRepository;
    private ModelMapperManager modelMapper;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              ModelMapperManager modelMapper, CarRepository carRepository) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new CustomerNotFoundException(companyId + " ID li bir şirket bulunamadı"));
    }

    @Override
    public String addNewCompany(Company company) {
        companyRepository.save(company);
        return company.getId() + " ID li şirket sisteme kaydedildi!";
    }

    @Override
    public String deleteCompanyById(Long companyId) {
        companyRepository.deleteById(companyId);
        return companyId + " ID li şirket sistemden çıkarıldı!";
    }

    @Override
    @Transactional
    public List<CarDto> getRentalCarsOfCompany(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CustomerNotFoundException(companyId + " ID li bir şirket bulunmamaktadır!"));
        List<Car> cars = carRepository.findByCompanies(company);
        return cars.stream()
                .map(car -> modelMapper.forResponse().map(car, CarDto.class))
                .collect(Collectors.toList());
    }

    // Satın alırken şirketin bütçesinden aracın fiyatını çıkartmayı yap!!!
    @Override
    public String purchaseCarToCompany(PurchaseCarToCompanyVm carToCompanyVm) {
        List<Car> cars = new ArrayList<Car>();
        for (Long carId: carToCompanyVm.getCars()) {
            Car car = carRepository.findById(carId)
                    .orElseThrow(() -> new CarNotFoundException(carId + " ID li bir araba bulunmamaktadır!"));
            cars.add(car);
        }

        Company company = companyRepository.findById(carToCompanyVm.getCompanyId())
                .orElseThrow(() -> new CustomerNotFoundException("Bu ID numarasına sahip bir şirket bulunmamaktadır!"));
        company.setCars(cars);
        companyRepository.save(company);
        return company.getId() + " ID li şirkete yeni arabalar alındı!";
    }
}