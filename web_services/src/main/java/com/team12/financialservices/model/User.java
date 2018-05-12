package com.team12.financialservices.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Table(name="user")
@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String userName;

    private String password;

    private String firstName;

    private String lastName;

    private String address;

    private String city;

    private String state;

    private String zip;

    private String role;

    private String email;

    private Double cash;

    @OneToMany(mappedBy = "user")
    private Collection<Position> positions;

    @Override
    public String toString() {
        return this.userName;
    }
}
