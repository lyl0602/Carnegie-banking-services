package com.team12.financialservices.form;

import com.team12.financialservices.Validation.StringValidator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ChangePassword {

    @NotEmpty(message = "*Please provide your password")
    String old_password;
    @Size(min = 6, max = 30, message = "*Your password must have at least 6 characters")
    @NotEmpty(message = "*Please provide your password")
    String new_password;
    @Size(min = 6, max = 30, message = "*Your password must have at least 6 characters")
    @NotEmpty(message = "*Please provide your password")
    String new_password_confirmation;

    public ChangePassword(String old_password, String new_password, String new_password_confirmation) {
        this.old_password = old_password;
        this.new_password = new_password;
        this.new_password_confirmation = new_password_confirmation;
    }
}
