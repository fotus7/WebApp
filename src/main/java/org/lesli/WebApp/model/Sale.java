package org.lesli.WebApp.model;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name = "Sales")
public class Sale implements Comparable<Sale> {
    private Long id;
    private transient int rowNum;
    private Date date;
    private Company company;
    private Product product;
    private double amount;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Sale () {
    }
    public Sale (Date date, Company company, Product product, double amount, int rowNum) {
        this.date = date;
        this.company = company;
        this.product = product;
        this.amount = amount;
        this.rowNum = rowNum;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
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

    @Override
    public int compareTo(Sale o) {
        if (this.rowNum == o.rowNum) {

        }
        if (this.date.compareTo(o.date) == 0) {
            if (this.company.getName().compareTo(o.company.getName()) == 0) {
                if (this.product.getName().compareTo(o.product.getName()) == 0) {
                    if (this.amount == o.amount) {
                        return Integer.compare(this.rowNum, o.rowNum);
                    } else return Double.compare(this.amount, o.amount);
                } else return this.product.getName().compareTo(o.product.getName());
            } else return this.company.getName().compareTo(o.company.getName());
        } else return this.date.compareTo(o.date);
    }

    @Override
    public String toString () {
        return rowNum + " " + dateFormat.format(date) + " " + company.getName() + " " + product.getName() + " " + amount;
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Sale s)) return false;
        boolean result = this.date.equals(s.date) && this.company.equals(s.getCompany());
        result = result && this.product.equals(s.getProduct());
        result = result && (this.amount == s.getAmount());
        result = result && (this.rowNum == s.rowNum);
        return result;

    }

    @Override
    public int hashCode () {
        return date.hashCode() + company.hashCode() + product.hashCode() + (int) amount + rowNum;
    }
}

