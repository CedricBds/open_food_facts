package org.diginamic.model;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product_category")
public class ProductCategory {


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "productCategory", cascade = CascadeType.PERSIST)
    private Set<Product> products = new HashSet<Product>();

    @Id
    @Column(name="label", nullable = true, length = 255,unique = true)
    private String label;


    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
