package com.team12.financialservices.Validation;

import com.team12.financialservices.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class DateValidation implements ConstraintValidator<DateValid, Date> {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void initialize(DateValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(Date s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return false;
        }
        java.util.Date latestDateUtil = transactionRepository.getLatestDate();

        // today
        if (latestDateUtil == null) {
            latestDateUtil = new Date(System.currentTimeMillis());
            return ((java.util.Date)s).compareTo(latestDateUtil) >= 0;
        }

        return ((java.util.Date)s).compareTo(latestDateUtil) > 0;
    }
}

//        Date latestDate = transactionRepository.getLatestDate();
//        LocalDate latestDateLocalDate = null;
//        if (latestDate == null) {
//            // today
//            Date d = new Date(System.currentTimeMillis());
//        } else {
//            latestDateLocalDate = latestDate.toLocalDate().plusDays(1);
//        }
//
//        LocalDate sLocalDate = s.toLocalDate();
//        return latestDateLocalDate.compareTo(sLocalDate) <= 0;


//        LocalDate d1 = d.plusDays(1);
//
//
//        Date datePlusOne = Date.valueOf(d1);

//        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
//        String text = df.format(s);
//
//        if(!text.matches("((?:19|20)\\d\\d)/(0?[1-9]|1[012])/([12][0-9]|3[01]|0?[1-9])")){
//
//            return false;
//
//        }


//        java.util.Date d = s;
//
//        String check = d.toString();
//
//        java.util.Date l;
//
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//
//
//              l =  sdf.format(check);


//        java.util.Date date = s;
//        try {
//            date = sdf1.parse(s);
//        } catch (ParseException e) {
//            return false;


// Date sqlStartDate = new java.sql.Date(date.getTime());


//        Date compare = transactionRepository.getLatestDate();
//
//        java.util.Date date1 = compare;
//
//        if(date1.compareTo(date)>0){
//            return false;
//
//        }
//        if(date1.compareTo(date)==0){
//            return false;
//        }
//        return true;


//    }
//}
