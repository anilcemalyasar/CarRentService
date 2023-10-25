package com.carrental.service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {

    private String type;
    private String model;
    private String color;
    private int year;
    private int max_speed;
}

