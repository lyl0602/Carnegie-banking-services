package com.team12.financialservices.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
public class CreateFundForm {

    @NotBlank
    private String name;

    @NotBlank
    private String symbol;

    @NotBlank
    @Pattern(regexp = "\\d+(.\\d{0,2})?")
    private String initial_value;

    @Override
    public String toString() {
        return "CreateFund: \n" +
                "name: " + name + "\n" +
                "symbol: " + symbol + "\n";
    }
}
