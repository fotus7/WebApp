package org.lesli.ExcelParser;

public class Sales {
    private int id;
    private String date;
    private String company;
    private String product;
    private double amount;
    public Sales () {

    }
    public Sales (String date, String company, String product, double amount) {
        this.date = date;
        this.company = company;
        this.product = product;
        this.amount = amount;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setDate (String date) {
        this.date = date;
    }
    public String getDate () {
        return date;
    }
    public void setCompany (String date) {
        this.company = company;
    }
    public String getCompany () {
        return company;
    }
    public void setProduct (String date) {
        this.product = product;
    }
    public String getProduct () {
        return product;
    }
    public void setAmount (double amount) {
        this.amount = amount;
    }
    public double getAmount () {
        return amount;
    }
    public String toString () {
        return date + ", " + company + ", " + product + ", " + amount;
    }
}
