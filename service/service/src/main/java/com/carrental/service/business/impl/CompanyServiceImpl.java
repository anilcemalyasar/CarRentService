package com.carrental.service.business.impl;

import com.carrental.service.business.CompanyService;
import com.carrental.service.exceptions.CarNotFoundException;
import com.carrental.service.exceptions.CustomerNotFoundException;
import com.carrental.service.model.dto.CarDto;
import com.carrental.service.model.dto.CompanyDto;
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

    // CompanyDto dönmesi lazım burayı ayarla!!!
    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyDto> companyDtos = new ArrayList<CompanyDto>();
        for (Company company: companies) {
            List<CarDto> carDtos = new ArrayList<CarDto>();
            for (Car car: company.getCars()) {
                CarDto carDto = modelMapper.forResponse().map(car, CarDto.class);
                carDtos.add(carDto);
            }
            CompanyDto companyDto = modelMapper.forRequest().map(company, CompanyDto.class);
            companyDto.setCars(carDtos);
            companyDtos.add(companyDto);
        }
        return companyDtos;
    }

    @Override
    public CompanyDto getCompanyById(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CustomerNotFoundException(companyId + " ID li bir şirket bulunamadı"));
        List<CarDto> cars = new ArrayList<>();
        for (Car car: company.getCars()) {
            CarDto carDto = CarDto.builder()
                    .type(car.getType())
                    .model(car.getModel())
                    .color(car.getColor())
                    .year(car.getYear())
                    .maxSpeed(car.getMaxSpeed())
                    .rentalFee(car.getRentalFee())
                    .salePrice(car.getSalePrice())
                    .build();

            cars.add(carDto);
        }
        CompanyDto companyDto = modelMapper.forRequest().map(company, CompanyDto.class);
        companyDto.setCars(cars);
        return companyDto;
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
        double totalAmount = 0;
        for (Long carId: carToCompanyVm.getCars()) {
            Car car = carRepository.findById(carId)
                    .orElseThrow(() -> new CarNotFoundException(carId + " ID li bir araba bulunmamaktadır!"));
            cars.add(car);
            totalAmount += car.getSalePrice();
        }

        Company company = companyRepository.findById(carToCompanyVm.getCompanyId())
                .orElseThrow(() -> new CustomerNotFoundException("Bu ID numarasına sahip bir şirket bulunmamaktadır!"));

        if(company.getBudget() < totalAmount) {
            double difference = totalAmount - company.getBudget();
            throw new CustomerNotFoundException("Şirketin bütçesi bu arabaları satın almak için yetersiz! "
                    + difference + " TL bütçe eklentisi gerekmektedir!");
        }
        // Updating budget of company after purchasing cars
        company.setBudget(company.getBudget() - totalAmount);
        List<Car> prevCars = company.getCars();
        for (Car newCar: cars) {
            prevCars.add(newCar);
        }

        company.setCars(prevCars);
        companyRepository.save(company);
        return company.getId() + " ID li şirkete yeni arabalar alındı!";
    }
}