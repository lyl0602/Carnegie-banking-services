package com.team12.financialservices.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

import static com.team12.financialservices.model.Transaction.TransactionType.SELL_FUND;

@Entity
@Data
@NoArgsConstructor
public class Transaction {

    private static final int SELL_FUND = 0;
    private static final int BUY_FUND = 1;
    private static final int REQUEST_CHECK = 2;
    private static final int DEPOSIT_CHECK = 3;

    public enum TransactionType {
        SELL_FUND, BUY_FUND, REQUEST_CHECK, DEPOSIT_CHECK
    }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "fund_id")
    private Fund fund;

    private Date executeDate;

    private Double shares;

    // private String transactionType;
    private TransactionType transactionType;

    private Double amount;

    // pending sellfund transaction, execute date and amount are null
    public Transaction(Customer customer, Fund fund, Date executeDate, Double shares, TransactionType transactionType, Double amount) {
        this.customer = customer;
        this.fund = fund;
        this.executeDate = executeDate;
        this.shares = shares;
        this.transactionType = transactionType;
        this.amount = amount;
    }
}
