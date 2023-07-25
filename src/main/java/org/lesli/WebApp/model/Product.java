package org.lesli.WebApp.model;

import jakarta.persistence.*;

import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "Products")
public class Product {
    private Long id;
    private String name;
    private Set<Sale> sales = new TreeSet<>();
    public Product () {
    }

    public Product (String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        if (!(obj instanceof Product p)) return false;
        return this.name.equals(p.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
