package com.team12.financialservices.form;


import com.team12.financialservices.model.FundPriceHistory;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Collection;

@Data
@NoArgsConstructor
public class ViewCustomerFormEmp {
    private Long customerId;

    private Long fundId;

    private String fundName;

    private String fundSymbol;

    private Double sharesOwn;

   // private Double sharesAvailable;
    private Double totalPrice;

    //private Date execute_date;

    private Collection<FundPriceHistory> fundPriceHistoryList;

    @NotNull(message = "Please input shares to sell")
// @Digits(integer = 0, fraction = 3, message = "Please input a 3-digit number")
    private Double sharesSell;

    public ViewCustomerFormEmp(Long customerId, Long fundId, String fundName, String fundSymbol, Double sharesOwn, Double totalPrice, Double sharesSell) {
        this.customerId = customerId;
        this.fundId = fundId;
        this.fundName = fundName;
        this.fundSymbol = fundSymbol;
        this.sharesOwn = sharesOwn;
        this.totalPrice = totalPrice;
        this.sharesSell = sharesSell;
       // this.execute_date = execute_date;
      //  fundPriceHistoryList=fpl;
        //fundPriceHistoryList = f;
    }

}
