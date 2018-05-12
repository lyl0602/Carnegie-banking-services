package com.team12.financialservices.model;


import com.team12.financialservices.Validation.HtmlInjection;
import com.team12.financialservices.Validation.StringValidator;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
@Table(name="user")
@Entity
@Data
public class User {

    @Id
    @GeneratedValue

    private Long id;

    @Size(min = 6, max = 30, message = "The size of your username must be between 6 and 30")
    @NotEmpty(message = "*Please provide a user name")
    @Pattern(regexp = "[A-Za-z]+" ,message = "username should contain only letters")
//    @HtmlInjection
    private String userName;

    @Size(min = 6, max = 30, message = "*Your password must have at least 6 characters")
    @NotEmpty(message = "*Please provide your password")
//    @HtmlInjection
    private String password;

    @Size(min = 1, max = 20, message = "The size of your username must be between 1 and 20")
    @NotEmpty(message = "*Please provide first name")
//    @SafeHtml
    @StringValidator
    private String firstName;

    @Size(min = 1, max = 20, message = "The size of your username must be between 1 and 20")
    @NotEmpty(message = "*Please provide last name")
    @StringValidator
    private String lastName;

    @Size(min = 6, max = 30, message = "*Your password must have at least 6 characters")
    @NotEmpty(message = "*Please provide your password")
    @Transient
    private String confirmpassword;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Role role;

    @Override
    public String toString() {
        return this.userName;
    }
}
