package com.team12.financialservices.service.Impl.employee;

import com.team12.financialservices.model.Fund;
import com.team12.financialservices.repository.FundRepository;
import com.team12.financialservices.service.employee.TransitionDayService;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TransitionDayServiceImpl implements TransitionDayService {
    @Autowired
    private FundRepository fundRepository;

    @Override
    public synchronized void transitionDay() throws Exception {

        List<Fund> funds = fundRepository.findAll();
        for (Fund fund : funds) {
            Double old = fund.getValue();

            //double gg =	 old + (double)(Math.random() * (((old*1.1) - (old*0.9)) + 1))	;
            int var = (int) (Math.random() * (20 + 1));
            var = 90 + var;
            double ne = (old * var) / 100;
            DecimalFormat df = new DecimalFormat("0.00");
            fund.setValue(Double.valueOf(df.format(ne)));

            fundRepository.save(fund);
        }
    }
}

