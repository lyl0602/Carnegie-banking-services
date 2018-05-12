package com.team12.financialservices.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RequestCheckForm {

    @NotBlank
    @Pattern(regexp = "\\d+(\\.\\d{0,2})?")
    private String cashValue;

    @Override
    public String toString() {
        return "RequestCheck: \n" +
                "cashValue: " + cashValue + "\n";
    }

}