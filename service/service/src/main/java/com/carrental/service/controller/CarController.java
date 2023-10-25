package com.carrental.service.controller;

import com.carrental.service.business.CarService;
import com.carrental.service.model.entity.Car;
import com.carrental.service.model.vm.UpdateCarColorVm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{carId}/image")
    public ResponseEntity<?> downloadCarImage(@PathVariable("carId") Long carId) {
        return  ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(carService.downloadCarImage(carId));
    }

    @PatchMapping("/color/update")
    public ResponseEntity<String> updateCarColor(@RequestBody UpdateCarColorVm carVm) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.updateCarColor(carVm));
    }

    @PatchMapping("/{carId}/maxSpeed/update/{newSpeed}")
    public ResponseEntity<String> updateMaxSpeed(@PathVariable("carId") Long carId,
                                                 @PathVariable("newSpeed") int newSpeed) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(carService.updateMaxSpeed(carId, newSpeed));
    }

}