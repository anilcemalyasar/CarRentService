package com.carrental.service.business;

import com.carrental.service.model.entity.Car;
import com.carrental.service.model.vm.UpdateCarColorVm;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarService {
    List<Car> getAllCars();
    Car getCarById(Long id);
    String addNewCar(Car car);
    String deleteById(Long id);
    String uploadImageToCar(Long carId, MultipartFile file) throws IOException;
    byte[] downloadCarImage(Long carId);
    String updateCarColor(UpdateCarColorVm carVm);
    String updateMaxSpeed(Long carId, int newSpeed);
}