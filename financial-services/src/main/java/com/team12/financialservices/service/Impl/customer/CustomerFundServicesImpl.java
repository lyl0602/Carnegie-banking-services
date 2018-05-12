package com.team12.financialservices.service.Impl.customer;

import com.team12.financialservices.form.BuyFundForm;
import com.team12.financialservices.form.FundListForm;
import com.team12.financialservices.form.ResearchFundForm;
import com.team12.financialservices.form.SellFundForm;
import com.team12.financialservices.model.*;
import com.team12.financialservices.repository.*;
import com.team12.financialservices.service.customer.CustomerFundServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerFundServicesImpl implements CustomerFundServices {
    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FundPriceHistoryRepository fundPriceHistoryRepository;

    @Override
    public boolean buyFund(Long fund_id, Double amountBuy, User user)  throws Exception{
        Fund fund = fundRepository.findById(fund_id);
        Double cash = ((Customer) user).getCash();
        Double balanceAvailable = ((Customer) user).getBalanceAvailable();
        // valid
        if (Double.compare(balanceAvailable, amountBuy) >= 0) {

            // save to user db
            // TODO to check
//            ((Customer) user).setCash(cash - amountBuy);
            ((Customer) user).setBalanceAvailable(balanceAvailable - amountBuy);
            user.setConfirmpassword(user.getPassword());
            userRepository.save(user);

            // save to transaction history db
            // Customer customer, Double shares, Transaction.TransactionType transactionType
            Transaction transaction = new Transaction((Customer) user, fund, null, null, Transaction.TransactionType.BUY_FUND, amountBuy);
            transactionRepository.save(transaction);
            return true;
        } else { // not valid
            return false;
        }
    }

    @Override
    public boolean sellFund(Long fund_id, Double sharesSell, User user)  throws Exception{
        Fund fund = fundRepository.findById(fund_id);
        Position position = positionRepository.findByPositionPK(new PositionPK(fund, (Customer) user));
        Double sharesAvailable = position.getSharesAvailable();
        // valid
        if (Double.compare(sharesAvailable, sharesSell) >= 0) {

            // save to position db, problem when save
            position.setSharesAvailable(sharesAvailable - sharesSell);
            positionRepository.save(position);

            // save to transaction history db
            // Customer customer, Double shares, Transaction.TransactionType transactionType
            Transaction transaction = new Transaction((Customer) user, fund, null, sharesSell, Transaction.TransactionType.SELL_FUND, null);
            transactionRepository.save(transaction);
            return true;
        } else { // not valid
            return false;
        }
    }

    @Override
    public void researchFund() {

    }

    @Override
    public SellFundForm getCustomerSellFundForm(User user, Long fundId)  throws Exception{

        Fund fund = fundRepository.findById(fundId);

        Position position = positionRepository.findByPositionPK(new PositionPK(fund, (Customer) user));

        //Long customerId, Long fundId, String fundName, String fundSymbol, double sharesOwn, double sharesSell
        String fundName = fund.getName();
        String fundSymbol = fund.getSymbol();
        Double sharesOwn;
        Double sharesAvailable;
        // customer does not own this fund
        if (position == null) {
            sharesOwn = 0d;
            sharesAvailable = 0d;
        } else { // customer owns this fund
            sharesOwn = position.getShares();
            sharesAvailable = position.getSharesAvailable();
        }

        return new SellFundForm(fundId, fundName, fundSymbol, sharesOwn, sharesAvailable, null);
    }

    @Override
    public BuyFundForm getCustomerBuyFundForm(User user, Long fundId)  throws Exception{

        Fund fund = fundRepository.findById(fundId);

        Position position = positionRepository.findByPositionPK(new PositionPK(fund, (Customer) user));

//        Long fundId, String fundName, String fundSymbol, Double cash, Double balanceAvailable, Double sharesOwn, Double sharesAvailable, Double amountBuy
        String fundName = fund.getName();
        String fundSymbol = fund.getSymbol();
        Double cash = ((Customer) user).getCash();
        Double balanceAvailable = ((Customer) user).getBalanceAvailable();
        Double sharesOwn;
        Double sharesAvailable;
        // customer does not own this fund
        if (position == null) {
            sharesOwn = 0d;
            sharesAvailable = 0d;
        } else { // customer owns this fund
            sharesOwn = position.getShares();
            sharesAvailable = position.getSharesAvailable();
        }

        return new BuyFundForm(fundId, fundName, fundSymbol, cash, balanceAvailable, sharesOwn, sharesAvailable, null);
    }

    @Override
    public SellFundForm updateCustomerSellFundForm(User user, SellFundForm form)  throws Exception{
        Fund fund = fundRepository.findById(form.getFundId());
        form.setFundName(fund.getName());
        form.setFundSymbol(fund.getSymbol());
        Position position = positionRepository.findByPositionPK(new PositionPK(fund, (Customer) user));
        form.setSharesOwn(position.getShares());
        form.setSharesAvailable(position.getSharesAvailable());
        return form;
    }

    @Override
    public BuyFundForm updateCustomerBuyFundForm(User user, BuyFundForm form)  throws Exception{
//        Long fundId, String fundName, String fundSymbol, Double cash, Double balanceAvailable, Double sharesOwn, Double sharesAvailable, Double amountBuy
        Fund fund = fundRepository.findById(form.getFundId());
        form.setFundName(fund.getName());
        form.setFundSymbol(fund.getSymbol());
        Position position = positionRepository.findByPositionPK(new PositionPK(fund, (Customer) user));
        if (position == null) {
            position = new Position();
            position.setPositionPK(new PositionPK(fund, (Customer) user));
            position.setShares(0.0);
            position.setSharesAvailable(0.0);
        }
        form.setSharesOwn(position.getShares());
        form.setSharesAvailable(position.getSharesAvailable());
        form.setCash(((Customer) user).getCash());
        form.setBalanceAvailable(((Customer) user).getBalanceAvailable());
        return form;
    }

    @Override
    public List<FundListForm> getAllFundForm(User user)  throws Exception{
        List<Fund> allFunds = fundRepository.findAll();
        List<FundListForm> fundListForms = new ArrayList<>();
        for (Fund fund : allFunds) {
            Double price = fundPriceHistoryRepository.findLatestHistoryPrice(fund.getId());
            Position position = positionRepository.findByPositionPK(new PositionPK(fund, (Customer) user));
            if (position == null) {
                fundListForms.add(new FundListForm(fund, price, new Double("0"), new Double("0")));
            } else {
                Double sharesOwn = position.getShares();
                Double sharesAvailable = position.getSharesAvailable();
                fundListForms.add(new FundListForm(fund, price, sharesOwn, sharesAvailable));
            }
        }
        return fundListForms;
    }

    @Override
    public ResearchFundForm getCustomerResearchFundForm(Long fund_id)  throws Exception{
        Fund fund = fundRepository.findById(fund_id);
        String fundName = fund.getName();
        String fundSymbol = fund.getSymbol();

        List<FundPriceHistory> histories = fundPriceHistoryRepository.findByFundPriceHistoryPK_Fund(fund);

        return new ResearchFundForm(fundName, fundSymbol, histories);

    }
}
