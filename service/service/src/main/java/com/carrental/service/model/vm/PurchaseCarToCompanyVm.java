package com.carrental.service.model.vm;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseCarToCompanyVm {

    @NotNull
    private Long companyId;

    @NotNull
    private List<Long> cars;
}