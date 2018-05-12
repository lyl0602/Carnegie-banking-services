package com.team12.financialservices.form;

import com.team12.financialservices.Validation.HtmlInjection;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class BuyFundForm {
    private Long fundId;

    private String fundName;

    private String fundSymbol;

    private Double cash;

    private Double balanceAvailable;

    private Double sharesOwn;

    private Double sharesAvailable;


    @NotNull(message = "Please input amount to buy")
//    @Min(value = 1, message = "At least buy one dollar")
    @DecimalMin(value = "0", inclusive = false, message = "Please input a valid number")
    @DecimalMax(value = "1000000000", inclusive = true, message = "Please input a valid number")
    @Digits(integer = 20, fraction = 2, message = "Please input a valid number")
    @Valid
    private Double amountBuy;

    // for fund list's submit button
    private String buyOrResearch;


    public BuyFundForm(Long fundId, String fundName, String fundSymbol, Double cash, Double balanceAvailable, Double sharesOwn, Double sharesAvailable, Double amountBuy) {
        this.fundId = fundId;
        this.fundName = fundName;
        this.fundSymbol = fundSymbol;
        this.cash = cash;
        this.balanceAvailable = balanceAvailable;
        this.sharesOwn = sharesOwn;
        this.sharesAvailable = sharesAvailable;
        this.amountBuy = amountBuy;
    }
}
