package com.carrental.service.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String mailAddress;
    @NotNull
    private int age;
    @NotNull
    private double wallet;
    
}