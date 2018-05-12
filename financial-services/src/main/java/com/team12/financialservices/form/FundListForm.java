package com.team12.financialservices.form;

import com.team12.financialservices.model.Fund;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Future;

@Data
@NoArgsConstructor
public class FundListForm {

    private Fund fund;

    private Double price;

    private Double sharesOwn;
@Future
    private Double sharesAvailable;

    public FundListForm(Fund fund, Double price, Double sharesOwn, Double sharesAvailable) {
        this.fund = fund;
        this.price = price;
        this.sharesOwn = sharesOwn;
        this.sharesAvailable = sharesAvailable;
    }
}
