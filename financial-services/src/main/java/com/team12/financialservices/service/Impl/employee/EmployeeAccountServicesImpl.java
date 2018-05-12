package com.team12.financialservices.service.Impl.employee;

import com.team12.financialservices.form.ViewCustomerFormEmp;
import com.team12.financialservices.model.*;
import com.team12.financialservices.repository.*;
import com.team12.financialservices.service.employee.EmployeeAccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class EmployeeAccountServicesImpl implements EmployeeAccountServices {
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private FundPriceHistoryRepository fundPriceHistoryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // return messages, success or error messages
    @Override
    public List<String> changeEmployeePassword(String old_password,
                                               String new_password,
                                               String new_password_confirmation,
                                               Authentication authentication)
            throws Exception{


        List<String> messages = new ArrayList<>();

        User user = userRepository.findByUserName(authentication.getName());

       //if()

        //TODO need to verify if the old password is correct
        //TODO need to encrypt password
//        String password = user.getPassword();

        if (new_password.equals(new_password_confirmation) && user.getPassword().equals(old_password)) {
            user.setPassword(new_password);
            user.setConfirmpassword(new_password_confirmation);
            userRepository.save(user);
            messages.add("Successfully update password");
        } else {
            messages.add("Password and Confirmation password do not match or Old Password is incorrect");
        }



        return messages;
    }

    @Override
    public List<String> resetCustomerPassword(String userName,String password,String confirmpassword)  throws Exception{

        User user = userRepository.findByUserName(userName);
        System.out.println(user.getUserName());
        List<String> messages = new ArrayList<>();

        if(user.getRole().getName().equals("ROLE_EMPLOYEE")) {
            messages.add("Not allowed");
        }

        else if(user==null){
            messages.add("No such Customer");

        }
        else if(password==null|password.trim().equals("")){
            messages.add("Password cannot be null");

        }
        else if(confirmpassword==null|confirmpassword.trim().equals("")){
            messages.add("Confirm Password cannot be null");

        }
        else if(!password.equals(confirmpassword)){
            messages.add("Password Doesn't match");
            //return messages;
        }
        else{
            try{
                //System.out.println("Save Save Save");
               user.setPassword(password);
               user.setConfirmpassword(confirmpassword);
               userRepository.save(user);
               messages.add("Updated Successfully");

            }
            catch (Exception e){
                messages.add("Something not right");
                return messages;
            }

            //System.out.println(i);
           // messages.add("Updated successfully");
        }
        return messages;












    }

    @Override
    public void createEmployeeAccount(User user) {
        Role emp = roleRepository.findByName("ROLE_EMPLOYEE");
      // Role emp = new Role("Role_Employee");
        user.setRole(emp);
        userRepository.save(user);






    }

    @Override
    public void createCustomerAccount(Customer customer) {
        Role cust = roleRepository.findByName("ROLE_CUSTOMER");
        customer.setRole(cust);
        customer.setCash(0.00);
        customer.setBalanceAvailable(customer.getCash());

        customerRepository.save(customer);



    }

    @Override
    public Customer viewCustomerAccount(String customer) {
        Customer customer1 = customerRepository.findByUserName(customer);

       return  customer1;


    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public List<ViewCustomerFormEmp> getCustomerOwnFunds(User user) {
        List<ViewCustomerFormEmp> forms = new ArrayList<>();
        // find position(customer id, fund id, shares)
        List<Transaction> transactions = transactionRepository.findAll();
        //List<Position> positions = positionRepository.findByPositionPK_Customer((Customer)user);
        List<Position> positions = positionRepository.findBySharesAndCustomerId(user.getId());
        for(Position position:positions) {

            //Long customerId, Long fundId, String fundName, String fundSymbol, double sharesOwn, double sharesSell
            Long customerId = user.getId();
//            Long fundId = position.getFund().getId();
//            String fundName = position.getFund().getName();
//            String fundSymbol = position.getFund().getSymbol();
            Long fundId = position.getPositionPK().getFund().getId();
            System.out.println(fundId);
            String fundName = position.getPositionPK().getFund().getName();
            String fundSymbol = position.getPositionPK().getFund().getSymbol();

            Double sharesOwn = position.getShares();
            Double sharesAvailable = position.getSharesAvailable();
            Double totalPrice=0.0;

            if((fundPriceHistoryRepository.findLatestHistoryPrice(fundId)!=null))
             totalPrice = sharesOwn * (fundPriceHistoryRepository.findLatestHistoryPrice(fundId));


            System.out.println(totalPrice);
//            Date d = null;
//          for(Transaction t:transactions){
//             if(t.getFund()!=null && t.getCustomer().getId()!=null&& t.getCustomer().getId()==customerId && t.getFund().getId() == fundId){
//                // if(t.getExecuteDate().equals(transactionRepository.findTopByExecuteDate(t.getCustomer().getId(),t.getFund().getId()).getExecuteDate())){
//                            d=t.getExecuteDate();
//                // }
//
//             }
//          }



           // Collection<FundPriceHistory> x= position.getFund().getHistories();

            ViewCustomerFormEmp form = new ViewCustomerFormEmp(customerId, fundId,fundName,fundSymbol,sharesOwn, totalPrice, null);
            forms.add(form);
        }

        return forms;
    }

}
