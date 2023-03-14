package org.lesli.ExcelParser;

public class Sales {
    String date;
    String company;
    String product;
    double amount;
    public Sales (String date, String company, String product, double amount) {
        this.date = date;
        this.company = company;
        this.product = product;
        this.amount = amount;
    }
    public String toString () {
        return date + ", " + company + ", " + product + ", " + amount;
    }
}
