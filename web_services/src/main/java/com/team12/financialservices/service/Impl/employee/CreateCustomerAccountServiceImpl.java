package com.team12.financialservices.service.Impl.employee;

//import com.team12.financialservices.controller.employee.CreateCustomerAccountForm;
//import com.team12.financialservices.controller.employee.CreateCustomerAccountForm;
import com.team12.financialservices.form.CreateCustomerAccountForm;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.employee.CreateCustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CreateCustomerAccountServiceImpl implements CreateCustomerAccountService {

    @Autowired
    UserRepository userRepository;
    @Override
    public synchronized boolean createCustomer(CreateCustomerAccountForm createCustomerAccountForm) throws Exception {

        if(userRepository.findByUserName(createCustomerAccountForm.getUsername())!=null) {
            return false;
        }

        else {

            User user = new User();
            user.setFirstName(createCustomerAccountForm.getFname());
            user.setLastName(createCustomerAccountForm.getLname());
            user.setAddress(createCustomerAccountForm.getAddress());
            user.setCity(createCustomerAccountForm.getCity());
            user.setEmail(createCustomerAccountForm.getEmail());
            user.setPassword(createCustomerAccountForm.getPassword());
            user.setState(createCustomerAccountForm.getState());
            user.setZip(createCustomerAccountForm.getZip());
            if (createCustomerAccountForm.getCash() == null || createCustomerAccountForm.getCash().length() == 0) {
                user.setCash(Double.valueOf("0.00"));
            } else {
                user.setCash(Double.valueOf(createCustomerAccountForm.getCash()));

            }
            user.setUserName(createCustomerAccountForm.getUsername());
            user.setRole("Customer");

            userRepository.save(user);
            return true;


        }

    }
}
