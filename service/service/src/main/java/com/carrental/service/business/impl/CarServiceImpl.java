package com.carrental.service.business.impl;

import com.carrental.service.business.CarService;
import com.carrental.service.exceptions.CarNotFoundException;
import com.carrental.service.model.dto.CarDto;
import com.carrental.service.model.entity.Car;
import com.carrental.service.model.vm.UpdateCarColorVm;
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
    public String addNewCar(CarDto carDto) {
        Car car = Car.builder()
                .type(carDto.getType())
                .model(carDto.getModel())
                .color(carDto.getColor())
                .year(carDto.getYear())
                .maxSpeed(carDto.getMaxSpeed())
                .rentalFee(carDto.getRentalFee())
                .build();
        // car.setIsAvailable(true);
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

    @Override
    public String updateCarColor(UpdateCarColorVm carVm) {
        Car car = carRepository.findById(carVm.getId()).get();
        String prevColor = car.getColor();
        car.setColor(carVm.getNewColor());
        carRepository.save(car);
        return car.getId() + " numaralı aracın rengi "
                + prevColor + " renginden " + carVm.getNewColor() + " rengine dönüştürüldü";
    }

    @Override
    public String updateCarRentalFee(Long carId, double newRentalFee) {
        Car car = carRepository.findById(carId)
                .orElseThrow(CarNotFoundException::new);

        car.setRentalFee(newRentalFee);
        return carId + " numaralı arabanın kiralama ücreti "
                + newRentalFee + " TL olarak güncellendi!";
    }

    @Override
    public String updateMaxSpeed(Long carId, int newSpeed) {
        if(!exists(carId)) {
            logger.error(carId + " ID numarasına sahip bir araç bulunmamaktadır!");
        }
        Car car = carRepository.findById(carId).get();
        int prevMaxSpeed = car.getMaxSpeed();
        car.setMaxSpeed(newSpeed);
        carRepository.save(car);
        return car.getId() + " numaralı aracın maksimum hızı "
                + prevMaxSpeed + " hızından " + newSpeed + " hızına dönüştürüldü";
    }

    public boolean exists(Long id) {
        Optional<Car> optional = carRepository.findById(id);
        return optional.isPresent();
    }
}