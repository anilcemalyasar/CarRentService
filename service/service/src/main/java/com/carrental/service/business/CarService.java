package com.carrental.service.business;

import com.carrental.service.model.dto.CarDto;
import com.carrental.service.model.entity.Car;
import com.carrental.service.model.vm.UpdateCarColorVm;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarService {
    List<Car> getAllCars();
    Car getCarById(Long id);
    String addNewCar(CarDto carDto);
    String deleteById(Long id);
    String uploadImageToCar(Long carId, MultipartFile file) throws IOException;
    byte[] downloadCarImage(Long carId);
    String updateCarColor(UpdateCarColorVm carVm);
    String updateCarRentalFee(Long carId, double newRentalFee);
    String updateMaxSpeed(Long carId, int newSpeed);
}