package com.carrental.service.business.impl;

import com.carrental.service.business.CarService;
import com.carrental.service.model.entity.Car;
import com.carrental.service.repository.CarRepository;
import com.carrental.service.util.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private CarRepository carRepository;
    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(Long id) {
        if(!exists(id)) {
            logger.error(id + " numaralı bir araba bulunmamakta!");
            return null;
        }
        Car car = carRepository.findById(id).get();
        return car;
    }

    @Override
    public String addNewCar(Car car) {
        carRepository.save(car);
        return car.getModel() + " sisteme eklendi!";
    }

    @Override
    public String deleteById(Long id) {
        carRepository.deleteById(id);
        return id + " numaralı araba sistemden silindi!";
    }

    @Override
    public String uploadImageToCar(Long carId, MultipartFile file) throws IOException {
        Car car = carRepository.findById(carId).get();
        car.setCarImage(ImageUtils.compressImage(file.getBytes()));
        return "Car file uploaded successfully : " + file.getOriginalFilename();
    }

    @Override
    public byte[] downloadCarImage(Long carId) {
        Car car = carRepository.findById(carId).get();
        byte[] images = ImageUtils.decompressImage(car.getCarImage());
        return images;
    }


    public boolean exists(Long id) {
        Optional<Car> optional = carRepository.findById(id);
        return optional.isPresent();
    }
}