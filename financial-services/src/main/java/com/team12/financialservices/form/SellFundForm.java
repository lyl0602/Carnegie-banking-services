package com.team12.financialservices.form;

import com.team12.financialservices.Validation.HtmlInjection;
import com.team12.financialservices.Validation.NumberValidator;
import com.team12.financialservices.Validation.StringValidator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
public class SellFundForm {

    private Long fundId;

    private String fundName;

    private String fundSymbol;

    private Double sharesOwn;

    private Double sharesAvailable;

    @NotNull(message = "Please input shares to sell")
//    @Min(value = 0, message = "Sell more than 0 share")
    @DecimalMin(value = "0", inclusive = false, message = "Please input a valid number")
    @DecimalMax(value = "1000000000", inclusive = true, message = "Please input a valid number")
    @Digits(integer = 20, fraction = 3, message = "Please input a valid number")
//    @NumberValidator
    @Valid
    private Double sharesSell;

    // for fund list's submit button
    private String buyOrResearch;

    public SellFundForm(Long fundId, String fundName, String fundSymbol, Double sharesOwn, Double sharesAvailable, Double sharesSell) {
        this.fundId = fundId;
        this.fundName = fundName;
        this.fundSymbol = fundSymbol;
        this.sharesOwn = sharesOwn;
        this.sharesAvailable = sharesAvailable;
        this.sharesSell = sharesSell;
    }



}
