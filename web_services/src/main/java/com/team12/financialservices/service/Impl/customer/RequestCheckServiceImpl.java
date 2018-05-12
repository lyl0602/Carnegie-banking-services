package com.team12.financialservices.service.Impl.customer;

import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.customer.RequestCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.team12.financialservices.form.RequestCheckForm;
import com.team12.financialservices.model.User;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RequestCheckServiceImpl implements RequestCheckService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean enoughCashInAccount(RequestCheckForm requestCheckForm, User user) throws Exception{
        return Double.compare(user.getCash(), Double.valueOf(requestCheckForm.getCashValue())) >= 0;
    }

    @Override
    public synchronized void requestCheck(RequestCheckForm requestCheckForm, User user) throws Exception{
        // update user
        user.setCash(user.getCash() - Double.valueOf(requestCheckForm.getCashValue()));
        userRepository.save(user);
    }
}


