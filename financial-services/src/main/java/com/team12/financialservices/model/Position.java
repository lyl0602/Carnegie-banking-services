package com.team12.financialservices.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
//@IdClass(PositionPK.class)
public class Position {
//    @Id
//    @ManyToOne()
//    @JoinColumn(name = "fund_id")
//    private Fund fund;
//
//    @Id
//    @ManyToOne()
//    @JoinColumn(name = "customer_id")
//    private Customer customer;

    @Embedded
    @Id
    private PositionPK positionPK;

    private Double shares;

    private Double sharesAvailable;





}


