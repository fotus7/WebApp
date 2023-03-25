package org.lesli.ExcelParser.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Products")
public class Product {
    private int id;
    private String name;
    private Set<Sale> sales = new HashSet<>();
    public Product () {
    }

    public Product (String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "product_id", unique = true, nullable = false)
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

    @OneToMany(mappedBy = "product")
    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Product)) return false;
        Product p = (Product) obj;
        return this.getName().equals(p.getName());
    }
}
