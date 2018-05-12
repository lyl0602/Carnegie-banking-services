package com.team12.financialservices.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@Table(name = "fund_price_history")
@NoArgsConstructor
//@IdClass(FundPriceHistoryPK.class)
public class FundPriceHistory {

    @Embedded
    @Id
    private FundPriceHistoryPK fundPriceHistoryPK;

//    @Id
//    @ManyToOne()
//    @JoinColumn(name = "fund_id")
//    private Fund fund;
//
//    @Id
//    private Date date;

    private double price;

    public FundPriceHistory(FundPriceHistoryPK fundPriceHistoryPK, double price) {
        this.fundPriceHistoryPK = fundPriceHistoryPK;
        this.price = price;
    }
}

//@Data
//class FundPriceHistoryPK implements Serializable{
//
//    private Fund fund;
//
//    private Date date;
//}
