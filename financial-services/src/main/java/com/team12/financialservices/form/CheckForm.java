package com.team12.financialservices.form;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class CheckForm {
    private Double cash;

    private Double balanceAvailable;

    private String userName;

    @NotNull(message = "Please input amount to request check")
    @Digits(integer = 20, fraction = 2, message = "Please input a valid number")
    @DecimalMin(value = "0", inclusive = false, message = "Please input a valid number")
    @DecimalMax(value = "1000000000", inclusive = true, message = "Please input a valid number")
    @Valid
    private Double requestOrDepositAmount;

    public CheckForm(Double cash, Double balanceAvailable, Double requestOrDepositAmount, String userName) {
        this.cash = cash;
        this.balanceAvailable = balanceAvailable;
        this.requestOrDepositAmount = requestOrDepositAmount;
        this.userName = userName;

    }
}
