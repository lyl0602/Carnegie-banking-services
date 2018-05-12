package com.team12.financialservices.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;


@Data
public class LoginForm {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Override
    public String toString() {
        return "Login: \n" +
                "username: " + username + "\n" +
                "password: " + password + "\n";
    }
}

