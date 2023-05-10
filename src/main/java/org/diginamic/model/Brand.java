package org.diginamic.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brand")
public class Brand {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "brand", cascade = CascadeType.PERSIST)
    private Set<Product> products = new HashSet<Product>();

    @Id
    @Column(name="name", nullable = true, length = 255, unique = true)
    private String name;

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
