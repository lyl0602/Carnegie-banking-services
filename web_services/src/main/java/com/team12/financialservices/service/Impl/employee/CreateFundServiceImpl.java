package com.team12.financialservices.service.Impl.employee;

import com.team12.financialservices.form.CreateFundForm;
import com.team12.financialservices.model.Fund;
import com.team12.financialservices.repository.FundRepository;
import com.team12.financialservices.service.employee.CreateFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.validation.Valid;
import java.util.Date;


@Service
public class CreateFundServiceImpl implements CreateFundService {
    @Autowired
    private FundRepository fundRepository;


    @Override
    public synchronized boolean createFund(CreateFundForm createFundForm) throws Exception{

        if (fundRepository.findBySymbol(createFundForm.getSymbol()) == null) {
            Fund fund = new Fund(createFundForm.getName(),
                    createFundForm.getSymbol(),
                    new Date(),
                    Double.valueOf(createFundForm.getInitial_value()));
            fundRepository.save(fund);
        }
        return true;
    }
}
