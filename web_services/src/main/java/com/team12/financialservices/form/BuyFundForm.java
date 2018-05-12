package com.team12.financialservices.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class BuyFundForm {

    @NotBlank
    private String symbol;

    @NotBlank
    @Pattern(regexp = "\\d+(\\.\\d{0,2})?")
    @Size(min = 1, max = 20)
    private String cashValue;

    @Override
    public String toString() {
        return "BuyFund: \n" +
                "symbol: " + symbol + "\n" +
                "cashValue: " + cashValue + "\n";
    }
}
