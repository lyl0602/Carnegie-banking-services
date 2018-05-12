package com.team12.financialservices.service.Impl.employee;

import com.team12.financialservices.form.TransitionDay;
import com.team12.financialservices.form.TransitionDayForm;
import com.team12.financialservices.model.*;
import com.team12.financialservices.repository.*;
import com.team12.financialservices.service.employee.EmployeeTransitionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class EmployeeTransitionServicesImpl implements EmployeeTransitionServices {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private FundPriceHistoryRepository fundPriceHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;


    @Override
    public TransitionDayForm getTransitionDayForm()  throws Exception{

//        Date latestDate = transactionRepository.getLatestDate();
        Date latestDate = fundPriceHistoryRepository.getLatestDate();
        Date date = null;
        if (latestDate == null) {
            // today
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String time = sdf.format(new java.util.Date());
            java.util.Date timeDate = null;
            try {
                timeDate = sdf.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            date = new java.sql.Date(timeDate.getTime());
        } else {

            // should be the latest transition day plus one
            LocalDate d = latestDate.toLocalDate();

            LocalDate d1 = d.plusDays(1);

            date = Date.valueOf(d1);
        }
        List<TransitionDay> transitionDayList = this.getAllTransitionDay();
        return new TransitionDayForm(transitionDayList, date);
    }


    @Override
    public TransitionDayForm updateCustomerSellFundForm(TransitionDayForm form) throws Exception {
        List<TransitionDay> transitionDayList = form.getTransitionDayList();
        List<TransitionDay> newTransitionDayList = new ArrayList<>();
        for (TransitionDay transitionDay : transitionDayList) {
            // fund id is null, return null
            if (transitionDay.getFundId() == null || fundRepository.findById(transitionDay.getFundId()) == null) {
                return null;
            }
            Fund fund = fundRepository.findById(transitionDay.getFundId());
            transitionDay.setFundName(fund.getName());
            transitionDay.setFundSymbol(fund.getSymbol());

            Double latestPrice = fundPriceHistoryRepository.findLatestHistoryPrice(fund.getId());
            transitionDay.setLatestPrice(latestPrice);

            newTransitionDayList.add(transitionDay);
        }
        form.setTransitionDayList(newTransitionDayList);
        return form;
    }

    @Override
    public void saveTransitionDay(TransitionDayForm form)  throws Exception{
        Date date = form.getDate();
        List<TransitionDay> transitionDayList = form.getTransitionDayList();
        DecimalFormat df = new DecimalFormat("###.##");

        for (TransitionDay transitionDay : transitionDayList) {
            // fundId, fundName, fundSymbol, latestPrice, newPrice
            Long fundId = transitionDay.getFundId();
            Fund fund = fundRepository.findById(fundId);
            Double newPrice = transitionDay.getNewPrice();

            // 0. create new column (fund_price_history table)
            FundPriceHistory fundPriceHistory = new FundPriceHistory(new FundPriceHistoryPK(fund, date), newPrice);
            fundPriceHistoryRepository.save(fundPriceHistory);


            List<Transaction> sellFundTransactions = transactionRepository.findByTransactionTypeAndFundAndExecuteDate(Transaction.TransactionType.SELL_FUND, fund, null);
            for (Transaction transaction : sellFundTransactions) {
                // 1. update sell fund (user table)
                // 1.1 cash: + share * newPrice
                // 1.2 balanceAvailable: + share * newPrice
                Double share = transaction.getShares();
                Customer user = (Customer) transaction.getCustomer();
                user.setCash(Double.valueOf(df.format(user.getCash() + share * newPrice)));
                user.setBalanceAvailable(Double.valueOf(df.format(user.getBalanceAvailable() + share * newPrice)));
                user.setConfirmpassword(user.getPassword());
                userRepository.save(user);

                // 2. update sell fund (position table)
                // 2.1 share: -share
                // 2.2 shareAvailable: ---
                Position position = positionRepository.findByPositionPK(new PositionPK(fund, user));
                position.setShares(position.getShares() - share);
                positionRepository.save(position);

                // 3. update sell fund (transaction table)
                // set amount: share * newPrice
                // execute date
                transaction.setExecuteDate(date);
                transaction.setAmount(Double.valueOf(df.format(share * newPrice)));
                transactionRepository.save(transaction);
            }


            List<Transaction> buyFundTransactions = transactionRepository.findByTransactionTypeAndFundAndExecuteDate(Transaction.TransactionType.BUY_FUND, fund, null);
            for (Transaction transaction : buyFundTransactions) {
                // 4. update buy fund (user table)
                // 4.1 cash: -----
                // TODO to check
                // 4.2 balanceAvailable: -amount  (do nothing)
                Double amount = transaction.getAmount();
                Customer user = (Customer) transaction.getCustomer();
                user.setCash(Double.valueOf(df.format(user.getCash() - amount)));
//                user.setBalanceAvailable(Double.valueOf(df.format(user.getBalanceAvailable() - amount)));
                user.setConfirmpassword(user.getPassword());
                userRepository.save(user);

                // 5. update buy fund (position table)
                // 5.1 share: + amount/newPrice
                // 5.2 shareAvailable: + amount/newPrice
                Position position = positionRepository.findByPositionPK(new PositionPK(fund, user));
                if (position == null) {
                    position = new Position();
                    position.setPositionPK(new PositionPK(fund, user));
                    position.setShares(Double.valueOf(df.format(amount/newPrice)));
                    position.setSharesAvailable(Double.valueOf(df.format(amount/newPrice)));
                } else {
                    position.setShares(Double.valueOf(df.format(position.getShares() + amount / newPrice)));
                    position.setSharesAvailable(Double.valueOf(df.format(position.getSharesAvailable() + amount / newPrice)));
                }

                positionRepository.save(position);

                // 6. update buy fund (transaction table)
                // set share: amount/newPrice
                // execute date
                Double test = Double.valueOf(df.format(amount / newPrice));
                transaction.setShares(Double.valueOf(df.format(amount / newPrice)));
                transaction.setExecuteDate(date);
                transactionRepository.save(transaction);
            }


        }

        List<Transaction> depositCheckTransactions = transactionRepository.findByTransactionTypeAndExecuteDate(Transaction.TransactionType.DEPOSIT_CHECK, null);
        for (Transaction transaction : depositCheckTransactions) {
            // 7. update deposit check (user table)
            // 7.1 cash: + amount
            // 7.2 balanceAvailable: + amount
            Double amount = transaction.getAmount();
            Customer user = (Customer) transaction.getCustomer();
            user.setCash(Double.valueOf(df.format(user.getCash() + amount)));
            user.setBalanceAvailable(Double.valueOf(df.format(user.getBalanceAvailable() + amount)));
            user.setConfirmpassword(user.getPassword());
            userRepository.save(user);

            // 8. update deposit check (transaction table)
            // set execute date
            transaction.setExecuteDate(date);
            transactionRepository.save(transaction);
        }


        // 9. update request check (user table)
        // 9.1 cash: - amount
        // 9.2 balanceAvailable: -----
        List<Transaction> requestCheckTransactions = transactionRepository.findByTransactionTypeAndExecuteDate(Transaction.TransactionType.REQUEST_CHECK, null);
        for (Transaction transaction : requestCheckTransactions) {
            Double amount = transaction.getAmount();
            Customer user = (Customer) transaction.getCustomer();
            user.setCash(Double.valueOf(df.format(user.getCash() - amount)));
            user.setConfirmpassword(user.getPassword());
            userRepository.save(user);

            // 10. update request check (transaction table)
            // set execute date
            transaction.setExecuteDate(date);
            transactionRepository.save(transaction);
        }

    }


    private Date getLatestTransitionDay() {

        return transactionRepository.getLatestDate();

    }

    private List<TransitionDay> getAllTransitionDay()  throws Exception{
        List<TransitionDay> transitionDayList = new ArrayList<>();
        List<Fund> funds = fundRepository.findAll();
        for (Fund fund : funds) {
            TransitionDay transitionDay = new TransitionDay();
            // fundId, fundName, fundSymbol, latestPrice, newPrice, error
            transitionDay.setFundId(fund.getId());
            transitionDay.setFundName(fund.getName());
            transitionDay.setFundSymbol(fund.getSymbol());

         //   System.out.println("fundid" + fund.getId());
            Double latestPrice = fundPriceHistoryRepository.findLatestHistoryPrice(fund.getId());
            transitionDay.setLatestPrice(latestPrice);
            transitionDay.setNewPrice(latestPrice);

            transitionDayList.add(transitionDay);
        }
        return transitionDayList;
    }
}
