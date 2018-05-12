package com.team12.financialservices.service.Impl.employee;

import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.employee.DepositCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.team12.financialservices.form.DepositCheckForm;
import com.team12.financialservices.model.User;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DepositCheckServiceImpl implements DepositCheckService {

    @Autowired
    UserRepository userRepository;

    @Override
    public synchronized void depositCheck(DepositCheckForm depositCheckForm) throws Exception{

        User customer = userRepository.findByUserName(depositCheckForm.getUsername());
        // update customer
        customer.setCash(customer.getCash() + Double.valueOf(depositCheckForm.getCash()));
        userRepository.save(customer);
    }
}
