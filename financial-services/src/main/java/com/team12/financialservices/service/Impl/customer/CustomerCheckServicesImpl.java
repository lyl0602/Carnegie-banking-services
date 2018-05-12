package com.team12.financialservices.service.Impl.customer;

import com.team12.financialservices.form.CheckForm;
import com.team12.financialservices.model.Customer;
import com.team12.financialservices.model.Transaction;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.TransactionRepository;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.customer.CustomerCheckServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerCheckServicesImpl implements CustomerCheckServices{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public boolean RequestCheck(Double amountRequest, User user) throws Exception{
        Double balanceAvailable = ((Customer)user).getBalanceAvailable();
        // valid
        if (Double.compare(balanceAvailable, amountRequest) >= 0) {
            // save to user db
            // decrease balance available amount
            ((Customer)user).setBalanceAvailable(balanceAvailable - amountRequest);
            user.setConfirmpassword(user.getPassword());
            userRepository.save(user);

            // save to transaction history db
            // Customer customer, Fund fund, Date executeDate, Double shares, TransactionType transactionType, Double amount
            Transaction transaction = new Transaction((Customer)user, null, null, null, Transaction.TransactionType.REQUEST_CHECK, amountRequest);
            transactionRepository.save(transaction);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public CheckForm getCustomerCheckForm(User user) throws Exception{
        User userUpdate = userRepository.findById(user.getId());
        Double cash = ((Customer)userUpdate).getCash();
        Double balanceAvailable = ((Customer)userUpdate).getBalanceAvailable();
        CheckForm form = new CheckForm(cash, balanceAvailable, null, user.getUserName());
        return form;
    }

    @Override
    public CheckForm updateCheckForm(CheckForm form, User user) throws Exception {
        form.setCash(((Customer)user).getCash());
        form.setBalanceAvailable(((Customer)user).getBalanceAvailable());
        form.setUserName(user.getUserName());
        return form;
    }

}
