package com.team12.financialservices.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Position {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fund_id")
    private Fund fund;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int shares;

    public Position(Fund fund, User user, Integer shares) {
        this.fund = fund;
        this.user = user;
        this.shares = shares;
    }
}
