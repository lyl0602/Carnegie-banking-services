package com.team12.financialservices.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class SellFundForm {

    @NotBlank
    private String symbol;

    @NotBlank
    @Pattern(regexp = "\\d+")
    private String numShares;

    @Override
    public String toString() {
        return "SellFund: \n" +
                "symbol: " + symbol + "\n" +
                "numShares: " + numShares + "\n";
    }
}
