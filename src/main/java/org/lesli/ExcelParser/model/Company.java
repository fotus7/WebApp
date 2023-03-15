package org.lesli.ExcelParser.model;

import java.util.Set;

public class Company {
    private int id;
    private String name;
    private Set<Product> products;
    public Company() {
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setName (String name) {
        this.name = name;
    }
    public String getName () {
        return name;
    }
}
