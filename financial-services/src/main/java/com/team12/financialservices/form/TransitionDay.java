package com.team12.financialservices.form;


import com.team12.financialservices.Validation.HtmlInjection;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
public class TransitionDay {

    @NotNull(message = "find id cannot be null")
    private Long fundId;

    private String fundName;

    private String fundSymbol;

    private Double latestPrice;

    @NotNull(message = "Please input a new price")
//    @Min(value = 0, message = "Please input a positive number")
    @Digits(integer = 20, fraction = 2, message = "Please input a valid number")
//    @Max(value=100000, message = "Please input a rational number")
    @DecimalMin(value = "0", inclusive = false, message = "Please input a valid number")
    @DecimalMax(value = "1000000000", inclusive = true, message = "Please input a valid number")
    @Valid
//    @NumberFormat(style= NumberFormat.Style.CURRENCY)
    private Double newPrice;

    private String error;

}
