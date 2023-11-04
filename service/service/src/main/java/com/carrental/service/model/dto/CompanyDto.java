package com.carrental.service.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    @NotBlank
    private String companyName;
    @Min(10000)
    private double budget;
    private String city;
    private String district;
    private String address;

}