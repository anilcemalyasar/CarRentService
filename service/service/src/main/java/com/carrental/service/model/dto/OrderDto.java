package com.carrental.service.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    @NotNull
    private Long carId;
    @NotNull
    private Long customerId;
    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date startTime;
    @NotBlank
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date endTime;


}