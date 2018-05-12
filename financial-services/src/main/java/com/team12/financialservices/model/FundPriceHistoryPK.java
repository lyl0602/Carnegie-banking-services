package com.team12.financialservices.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.sql.Date;

@Embeddable
@Data
@NoArgsConstructor
public class FundPriceHistoryPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "fund_id")
    private Fund fund;

    private Date date;

//
//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((fund == null) ? 0 : fund.hashCode());
//        result = prime * result
//                + ((date == null) ? 0 : date.hashCode());
//        return result;
//    }
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        FundPriceHistoryPK other = (FundPriceHistoryPK) obj;
//        if (fund == null) {
//            if (other.fund != null)
//                return false;
//        } else if (!fund.equals(other.fund))
//            return false;
//        if (date == null) {
//            if (other.date != null)
//                return false;
//        } else if (!date.equals(other.date))
//            return false;
//        return true;
//    }

    public FundPriceHistoryPK(Fund fund, Date date) {
        this.fund = fund;
        this.date = date;
    }
}