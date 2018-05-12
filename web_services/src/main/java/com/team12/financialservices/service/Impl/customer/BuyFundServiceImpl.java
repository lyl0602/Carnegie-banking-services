package com.team12.financialservices.service.Impl.customer;

import com.team12.financialservices.form.BuyFundForm;
import com.team12.financialservices.model.Fund;
import com.team12.financialservices.model.Position;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.FundRepository;
import com.team12.financialservices.repository.PositionRepository;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.customer.BuyFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;


@Service
public class BuyFundServiceImpl implements BuyFundService {

    @Autowired
    FundRepository fundRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PositionRepository positionRepository;

    @Override
    public boolean fundExist(BuyFundForm buyFundForm) throws Exception{
        return fundRepository.findBySymbol(buyFundForm.getSymbol()) != null;
    }

    @Override
    public boolean enoughCashInAccount(BuyFundForm buyFundForm, User user) throws Exception{
        return Double.compare(user.getCash(), Double.valueOf(buyFundForm.getCashValue())) >= 0;
    }

    @Override
    public boolean enoughCashProvided(BuyFundForm buyFundForm) throws Exception{
        Fund fund = fundRepository.findBySymbol(buyFundForm.getSymbol());
        return Double.compare(Double.valueOf(buyFundForm.getCashValue()), fund.getValue()) >= 0;
    }

    @Override
    public synchronized void buyFund(BuyFundForm buyFundForm, User user) throws Exception{
        Fund fund = fundRepository.findBySymbol(buyFundForm.getSymbol());
        Position position = positionRepository.findByFundAndUser(fund, user);

        int shares = (int)(Double.valueOf(buyFundForm.getCashValue()) / fund.getValue());

        // update position
        // + shares
        if (position == null) {
            position = new Position(fund,user,shares);
            positionRepository.save(position);
        } else {
            position.setShares(position.getShares() + shares);
            positionRepository.save(position);
        }

        // update user
        // - cost
        double cost = shares * fund.getValue();
        DecimalFormat df = new DecimalFormat("0.00");
        user.setCash(user.getCash() - Double.valueOf(df.format(cost)));
        userRepository.save(user);

    }
}
