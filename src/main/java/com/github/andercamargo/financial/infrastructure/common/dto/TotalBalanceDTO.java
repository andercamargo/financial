package com.github.andercamargo.financial.infrastructure.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TotalBalanceDTO {
    private LocalDate date;
    private int totalBalance;
}