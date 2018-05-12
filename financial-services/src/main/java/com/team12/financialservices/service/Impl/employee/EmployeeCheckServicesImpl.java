package com.team12.financialservices.service.Impl.employee;

import com.team12.financialservices.model.Customer;
import com.team12.financialservices.model.Transaction;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.TransactionRepository;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.employee.EmployeeCheckServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeCheckServicesImpl implements EmployeeCheckServices{

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean depositCheck(Double amountDeposit, String userName) throws Exception{

        //check if user exist
        if (userRepository.findByUserName(userName) == null) {
            return false;
        }
        if (!userRepository.findByUserName(userName).getRole().getName().equals("ROLE_CUSTOMER")) {
            return false;
        }
        // save to transaction history db
        // Customer customer, Fund fund, Date executeDate, Double shares, TransactionType transactionType, Double amount
        User user = userRepository.findByUserName(userName);
        Transaction transaction = new Transaction((Customer)user, null, null, null, Transaction.TransactionType.DEPOSIT_CHECK, amountDeposit);
        transactionRepository.save(transaction);
        return true;

    }
}
