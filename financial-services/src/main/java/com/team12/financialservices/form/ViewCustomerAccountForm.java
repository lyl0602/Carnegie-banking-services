package com.team12.financialservices.form;


import com.team12.financialservices.model.Customer;
import com.team12.financialservices.model.Position;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ViewCustomerAccountForm {

    private Customer customer;

    private List<PositionAndValue> position;

    private Date lastTransitionDay;

}

class PositionAndValue{
    private Position position;
    private Double value;
}
