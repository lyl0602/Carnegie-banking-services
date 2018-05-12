package com.team12.financialservices.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class DepositCheckForm{

    @NotBlank
    @Pattern(regexp = "\\d+(\\.\\d{0,2})?")
    private String cash;

    @NotBlank
    private String username;

    @Override
    public String toString() {
        return "DepositFund: \n" +
                "username: " + username + "\n" +
                "cash: " + cash + "\n";
    }
}