package com.team12.financialservices.controller.customer;


import com.team12.financialservices.form.BuyFundForm;
import com.team12.financialservices.form.FundListForm;
import com.team12.financialservices.form.ResearchFundForm;
import com.team12.financialservices.form.SellFundForm;
import com.team12.financialservices.model.Fund;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.FundRepository;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.customer.CustomerFundServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Binding;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;

@RestController
@RequestMapping("/customer")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerFundController {

    @Autowired
    private CustomerFundServices customerFundServices;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FundRepository fundRepository;

    @RequestMapping(value = "/sell_fund/{fund_id}", method = RequestMethod.GET)
    public ModelAndView customerSellFundGet(Principal principal,
                                            @PathVariable Long fund_id)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        User user = userRepository.findByUserName(principal.getName());

        // path variable fund_id does not exist
        if (fundRepository.findById(fund_id) == null) {
            modelAndView.setViewName("error/404");
            return modelAndView;
        }

        // funds to be displayed
        SellFundForm form = customerFundServices.getCustomerSellFundForm(user, fund_id);
        modelAndView.addObject("form", form);
        modelAndView.setViewName("customer/sell_fund");
        return modelAndView;
    }

    @RequestMapping(value = "/sell_fund", method = RequestMethod.POST)
    public ModelAndView customerSellFundPost(Principal principal,
                                             @ModelAttribute("form") @Valid SellFundForm form,
                                             BindingResult result)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();

//
//        if (result.getFieldError("sharesSell").getCodes()[0].matches("typeMismatch.*")) {
//            result.rejectValue("sharesSell","error.sharesSell", "Please input a number");
//        }

        User user = userRepository.findByUserName(principal.getName());
        Double sharesSell = form.getSharesSell();
        Long fund_id = form.getFundId();

        String successMessage = null;
        if (!result.hasErrors()) {

            boolean validShareNum = customerFundServices.sellFund(fund_id, sharesSell, user);
            if (!validShareNum) {
                result.rejectValue("sharesSell", "error.sharesSell", "You do not have enough shares available");
            } else {

                DecimalFormat formatter = new DecimalFormat("###,###.###");
                String shares  = formatter.format(form.getSharesSell());

                successMessage = "You have sold " + shares + " shares";
                modelAndView.addObject("successMessage", successMessage);

                // updated form to be displayed
                form = customerFundServices.getCustomerSellFundForm(user, fund_id);
                modelAndView.addObject("form", form);

            }

        }

        // update form
        form = customerFundServices.updateCustomerSellFundForm(user, form);
        modelAndView.addObject("form", form);

        modelAndView.setViewName("customer/sell_fund");
        return modelAndView;
    }

    @RequestMapping(value = "/fund_list", method = RequestMethod.GET)
    public ModelAndView customerViewFundListGet(Principal principal)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();

        User user = userRepository.findByUserName(principal.getName());

        // funds to be displayed
        List<FundListForm> forms = customerFundServices.getAllFundForm(user);
        modelAndView.addObject("forms", forms);
        modelAndView.setViewName("customer/fund_list");
        return modelAndView;
    }


    @RequestMapping(value = "/buy_fund/{fund_id}", method = RequestMethod.GET)
    public ModelAndView customerBuyFundGet(Principal principal,
                                            @PathVariable Long fund_id)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        User user = userRepository.findByUserName(principal.getName());

        // path variable fund_id does not exist
        if (fundRepository.findById(fund_id) == null) {
            modelAndView.setViewName("error/404");
            return modelAndView;
        }

        // funds to be displayed
        BuyFundForm form = customerFundServices.getCustomerBuyFundForm(user, fund_id);
        modelAndView.addObject("form", form);
        modelAndView.setViewName("customer/buy_fund");
        return modelAndView;
    }


    @RequestMapping(value = "/research_fund/{fund_id}", method = RequestMethod.GET)
    public ModelAndView customerResearchFundGet(Principal principal,
                                           @PathVariable Long fund_id)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        User user = userRepository.findByUserName(principal.getName());

        // path variable fund_id does not exist
        if (fundRepository.findById(fund_id) == null) {
            modelAndView.setViewName("error/404");
            return modelAndView;
        }

        // funds to be displayed
        ResearchFundForm form = customerFundServices.getCustomerResearchFundForm(fund_id);
        modelAndView.addObject("form", form);
        modelAndView.setViewName("customer/research_fund");
        return modelAndView;
    }



    @RequestMapping(value = "/buy_fund", method = RequestMethod.POST)
    public ModelAndView customerBuyFundPost(Principal principal,
                                             @ModelAttribute("form") @Valid BuyFundForm form,
                                             BindingResult result) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        User user = userRepository.findByUserName(principal.getName());
        Double amountBuy = form.getAmountBuy();
        Long fund_id = form.getFundId();

        String successMessage = null;
        if (!result.hasErrors()) {

            boolean validShareNum = customerFundServices.buyFund(fund_id, amountBuy, user);
            if (!validShareNum) {
                result.rejectValue("amountBuy", "error.amountBuy", "You do not have enough balance available");
            } else {
                DecimalFormat formatter = new DecimalFormat("###,###.###");
                String amount  = formatter.format(form.getAmountBuy());
                successMessage = "You have bought $" + amount + " amount of fund";
                modelAndView.addObject("successMessage", successMessage);

                // updated form to be displayed
                form = customerFundServices.getCustomerBuyFundForm(user, fund_id);
                modelAndView.addObject("form", form);

                return modelAndView;

            }

        }

        // update form
        form = customerFundServices.updateCustomerBuyFundForm(user, form);
        modelAndView.addObject("form", form);

        modelAndView.setViewName("customer/buy_fund");
        return modelAndView;
    }


}
