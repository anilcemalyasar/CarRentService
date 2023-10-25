package com.carrental.service.controller;

import com.carrental.service.business.CarService;
import com.carrental.service.model.entity.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/cars")
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{carId}")
    public Car getCarById(@PathVariable("carId") Long carId) {
        return carService.getCarById(carId);
    }

    @PostMapping("")
    public String addNewCar(@RequestBody Car car) {
        return carService.addNewCar(car);
    }

    @PostMapping("/{carId}/image")
    public String uploadImageToCar(@PathVariable("carId") Long carId
            ,@RequestParam("car_image") MultipartFile file) throws IOException {
        return carService.uploadImageToCar(carId, file);
    }

}