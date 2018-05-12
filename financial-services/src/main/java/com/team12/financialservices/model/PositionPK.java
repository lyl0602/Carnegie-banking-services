package com.team12.financialservices.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class PositionPK implements Serializable {

    //    @JoinTable(
//            name = "positions",
//            joinColumns = @JoinColumn(
//                    name = "fund_id", referencedColumnName = "fund_id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "fund_id", referencedColumnName = "id"))
    @ManyToOne
    @JoinColumn(name = "fund_id")
    private Fund fund;


    //    @JoinTable(
//            name = "positions",
//            joinColumns = @JoinColumn(
//                    name = "customer_id", referencedColumnName = "customer_id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "customer_id", referencedColumnName = "id"))
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


    //    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + ((fund == null) ? 0 : fund.hashCode());
//        result = prime * result
//                + ((customer == null) ? 0 : customer.hashCode());
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
//        PositionPK other = (PositionPK) obj;
//        if (fund == null) {
//            if (other.fund != null)
//                return false;
//        } else if (!fund.equals(other.fund))
//            return false;
//        if (customer == null) {
//            if (other.customer != null)
//                return false;
//        } else if (!customer.equals(other.customer))
//            return false;
//        return true;
//    }
//
    public PositionPK(Fund fund, Customer customer) {
        this.fund = fund;
        this.customer = customer;
    }
}
