package com.team12.financialservices.service.Impl.customer;

import com.team12.financialservices.form.ViewPortfolioForm;
import com.team12.financialservices.model.Position;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.PositionRepository;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.customer.ViewPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


@Service
public class ViewPortfolioServiceImpl implements ViewPortfolioService {
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<ViewPortfolioForm> getAllDetails(User user) throws Exception {


        List<ViewPortfolioForm> forms = new ArrayList<>();

        User user1 = userRepository.findByUserName(user.getUserName());

        List<Position> positions = positionRepository.findByUser(user1);

        for (Position position : positions) {


            if (position.getShares() == 0) continue;

            DecimalFormat df = new DecimalFormat("0.00");

            String name = position.getFund().getName();
            String shares = Integer.toString(position.getShares());
            String price = df.format(position.getFund().getValue());
            ViewPortfolioForm form = new ViewPortfolioForm(name, shares, price);
            forms.add(form);


        }


        return forms;

    }
}
