package com.team12.financialservices.form;

import com.team12.financialservices.model.FundPriceHistory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ResearchFundForm {

    private String fundName;

    private String fundSymbol;

    private List<FundPriceHistory> histories;

    public ResearchFundForm(String fundName, String fundSymbol, List<FundPriceHistory> histories) {
        this.fundName = fundName;
        this.fundSymbol = fundSymbol;
        this.histories = histories;
    }
}
