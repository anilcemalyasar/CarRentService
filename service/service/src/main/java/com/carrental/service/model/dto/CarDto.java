package com.carrental.service.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarDto {

    @NotBlank
    private String type;
    @NotBlank
    private String model;
    @NotBlank
    private String color;
    @Min(2000)
    private int year;
    @Min(120)
    private int maxSpeed;
    @Min(300)
    private double rentalFee;

    @Min(10000)
    private double salePrice;
}

