package com.team12.financialservices.service.Impl.customer;

import com.team12.financialservices.form.SellFundForm;
import com.team12.financialservices.model.Fund;
import com.team12.financialservices.model.Position;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.FundRepository;
import com.team12.financialservices.repository.PositionRepository;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.customer.SellFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;


@Service
public class SellFundServiceImpl implements SellFundService {

    @Autowired
    FundRepository fundRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean fundExist(SellFundForm sellFundForm) throws Exception{
        return fundRepository.findBySymbol(sellFundForm.getSymbol()) != null;
    }

    @Override
    public boolean enoughSharesInAccount(SellFundForm sellFundForm, User user) throws Exception{
        Fund fund = fundRepository.findBySymbol(sellFundForm.getSymbol());
        Position position = positionRepository.findByFundAndUser(fund, user);
        return Integer.compare(position.getShares(), Integer.valueOf(sellFundForm.getNumShares())) >= 0;
    }

    @Override
    public synchronized void sellFund(SellFundForm sellFundForm, User user) throws Exception{
        Fund fund = fundRepository.findBySymbol(sellFundForm.getSymbol());
        Position position = positionRepository.findByFundAndUser(fund, user);

        // update position
        // - shares
        position.setShares(position.getShares() - Integer.valueOf(sellFundForm.getNumShares()));
        positionRepository.save(position);
        // update user
        // + cash

        DecimalFormat df = new DecimalFormat("0.00");

        Double newCash = Double.valueOf(
                df.format(Integer.valueOf(sellFundForm.getNumShares())* fund.getValue()));
        user.setCash(user.getCash() + newCash);
        userRepository.save(user);
    }
}
