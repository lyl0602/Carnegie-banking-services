package com.team12.financialservices.service.Impl.customer;

import com.team12.financialservices.service.customer.CustomerTransactionServices;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerTransactionServicesImpl implements CustomerTransactionServices {
    @Override
    public void viewTransactionHistory()  throws Exception{

    }
}
