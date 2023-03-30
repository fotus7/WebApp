package org.lesli.WebApp.dto;

import java.util.Date;

public class SaleDto {

    private Date date;
    private String company;
    private String product;
    private double amount;

    public SaleDto (Date date, String company, String product, double amount) {
        this.date = date;
        this.company = company;
        this.product = product;
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
