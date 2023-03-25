package org.lesli.ExcelParser.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Companies")
public class Company {

    private int id;
    private String name;
    private Set<Sale> sales = new HashSet<>();
    public Company() {
    }

    public Company (String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "company_id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "company")
    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Company)) return false;
        Company c = (Company) obj;
        return this.getName().equals(c.getName());
    }
}
