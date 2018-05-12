package com.team12.financialservices.service.Impl.employee;

import com.team12.financialservices.model.Customer;
import com.team12.financialservices.model.Transaction;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.*;
import com.team12.financialservices.service.employee.EmployeeTransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeTransactionServicesImpl implements EmployeeTransactionServices {


    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<Transaction> viewCustomerTransactionHistory(String username)  throws Exception{


        Customer customer = customerRepository.findByUserName(username);


        List<Transaction> lists = transactionRepository.findByCustomerId(customer.getId());


        return lists;


    }
}
