package com.team12.financialservices.form;

import lombok.Data;

import java.util.List;

@Data
public class ViewPortfolioForm {


    private String name;
    private String shares;
    private String price;

    public ViewPortfolioForm( String name, String shares, String price) {

        this.name = name;
        this.shares = shares;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ViewPortfolio: \n" +
                "name: " + name + "\n" +
                "shares: " + shares + "\n" +
                "price: " + price + "\n";
    }


}
