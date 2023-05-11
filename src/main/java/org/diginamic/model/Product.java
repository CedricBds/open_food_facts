package org.diginamic.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "product")

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    // categorie
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "productCategory")
    private ProductCategory productCategory;

    // nom
    @Column(name = "label", nullable = true, length = 255)
    private String label;

    // marque
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "brand")
    private Brand brand;

    // ingredients
    @ManyToMany
    @JoinTable(name = "product_ingredient", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients;

    @Column(name = "grade", nullable = true, length = 255)
    private String grade;

    
    /** 
     * @return Long
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

}
