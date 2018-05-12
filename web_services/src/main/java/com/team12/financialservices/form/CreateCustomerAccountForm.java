package com.team12.financialservices.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class CreateCustomerAccountForm {

    @NotBlank
    private String fname;

    @NotBlank
    private String lname;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String zip;

    @NotBlank
    private String email;

    @Pattern(regexp = "\\d*(\\.\\d{0,2})?")
    private String cash;

    @NotBlank
    private String username;

    @NotBlank
    private String password;


    @Override
    public String toString() {
        return "CreateCustomerAccount: \n" +
                "fname: " + fname + "\n" +
                "lname: " + lname + "\n" +
                "address: " + address + "\n" +
                "city: " + city + "\n" +
                "state: " + state + "\n" +
                "zip: " + zip + "\n" +
                "email: " + email + "\n" +
                "cash: " + cash + "\n" +
                "username: " + username + "\n" +
                "password: " + password + "\n";
    }


}
