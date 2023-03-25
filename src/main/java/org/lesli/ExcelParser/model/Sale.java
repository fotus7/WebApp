package org.lesli.ExcelParser.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Sales")
public class Sale {
    private int id;
    private Date date;
    private Company company;
    private Product product;
    private double amount;

    public Sale () {
    }
    public Sale (Date date, Company company, Product product, double amount) {
        this.date = date;
        this.company = company;
        this.product = product;
        this.amount = amount;
    }

    @Id
    @GeneratedValue
    @Column(name = "sale_id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(nullable = false)
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

