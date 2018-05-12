package com.team12.financialservices.model;

import com.team12.financialservices.Validation.HtmlInjection;
import com.team12.financialservices.Validation.NumberValidator;
import com.team12.financialservices.Validation.StringValidator;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Collection;

@Entity
@Data
public class Customer extends User{

    @NotEmpty(message = "Please Enter Address")
    @Size(min = 1, max = 100, message = "The size must be between 1 and 100")
    private String addr_line1;

    //@Size(min = 1, max = 100, message = "The size must be between {min} and {max}")
    private String addr_line2;

    @NotEmpty(message = "Please Enter City")
    @StringValidator(message = "Please Don't Enter a number")
    @Size(min = 1, max = 100, message = "The size must be between 1 and 100")
    private String city;

    @NotEmpty(message = "Please Enter State")
    @Size(min = 1, max = 100, message = "The size must be between 1 and 100")
    @StringValidator(message = "Please Enter proper state")
    private String state;

    @NotEmpty(message = "Please Enter Zip")
    @Pattern(regexp = "[0-9-]*", message = "Please input numbers or '-' only")
    @Size(min = 1, max = 10, message = "The size must be between 1 and 10")
    private String zip;

    //@NumberValidator(message = "Please Enter a numerical value")
//    @NotNull(message = "Cash Cannot be empty")//@Value("#{new Double.parseDouble('${cash}')}")

//    //@NumberFormat(style= NumberFormat.Style.CURRENCY)
//    @DecimalMin(value = "0", inclusive = false, message = "Please input a valid number")
//    @DecimalMax(value = "1000000000", inclusive = true, message = "Please input a valid number")
//    //@Value("#{new Double('${user.cash}')}")

    //@NumberFormat(style= NumberFormat.Style.CURRENCY)
//    @DecimalMin(value = "0", inclusive = false, message = "Please input a valid number")
//    @DecimalMax(value = "1000000000", inclusive = true, message = "Please input a valid number")
    //@Value("#{new Double('${user.cash}')}")

//    @Digits(integer = 20, fraction = 2, message = "Please input a valid number")
//    @Valid
    private Double cash;


    @NumberFormat(style= NumberFormat.Style.CURRENCY)

    private Double balanceAvailable;


    @OneToMany
    private Collection<Position> positions;

    @OneToMany(mappedBy = "customer")
    private Collection<Transaction> transactions;

}
