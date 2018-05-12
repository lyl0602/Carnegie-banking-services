package com.team12.financialservices.form;


import com.team12.financialservices.Validation.DateValid;
import com.team12.financialservices.Validation.HtmlInjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class TransitionDayForm {

    @Valid
    private List<TransitionDay> transitionDayList;




    @DateValid
    private Date date;
   // @Pattern(regexp = "((?:19|20)\\d\\d)/(0?[1-9]|1[012])/([12][0-9]|3[01]|0?[1-9])")

//    @NotNull(message="Please input Date")
//    @DateTimeFormat
//    @DateValid(message = "Please input a valid date")



   // String x = date.toString();




    public TransitionDayForm(List<TransitionDay> transitionDayList, Date date) {
        this.transitionDayList = transitionDayList;
//        if(x.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$"))
//        {
//            this.date = date;
//        }
        this.date = date;




    }



}
