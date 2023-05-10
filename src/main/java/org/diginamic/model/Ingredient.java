package org.diginamic.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @Column(name = "name", nullable = true, length = 600, unique = true)
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private List<Product> products;

    public List<Product> getProduct() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
