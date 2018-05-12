package com.team12.financialservices.service.Impl.employee;

import com.team12.financialservices.model.Fund;
import com.team12.financialservices.repository.FundRepository;
import com.team12.financialservices.service.employee.EmployeeFundServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EmployeeFundServicesImpl implements EmployeeFundServices {
    @Autowired
    private FundRepository fundRepository;

    private final static int NAME = 0;
    private final static int SYMBOL = 1;

    @Override
    public boolean[] createFund(Fund fund, BindingResult result)  throws Exception{


//        if (fund == null) {
//            return ;
//        }

        boolean[] registered = new boolean[2];
        if (fundRepository.findByName(fund.getName()) != null) {
            registered[NAME] = true;
        }
        if (fundRepository.findBySymbol(fund.getSymbol()) != null) {
            registered[SYMBOL] = true;
        }
        if (!registered[NAME] && !registered[SYMBOL]) {
            fundRepository.save(fund);
        }

        return registered;
    }


}
