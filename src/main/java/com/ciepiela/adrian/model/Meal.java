package com.ciepiela.adrian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.naming.directory.InvalidAttributesException;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private long mealId;

    private String name;

    @OneToMany
    @Column(name = "meal_id")
    private List<Product> products = new ArrayList<>();

    @ElementCollection
    private List<Integer> weightOfProducts = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    private User user;

    //for jpa only
    private Meal() {
    }

    public Meal(String name) {
        this.name = name;
    }

    public long getMealId() {
        return mealId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products, List<Integer> weightOfProducts) throws Exception {
        if(products.size() != weightOfProducts.size()) {
            throw new InvalidAttributesException("Number of products and weightOfProducts is different");
        }
        this.products = products;
        this.weightOfProducts = weightOfProducts;
    }

    public void updateProduct(Product oldProduct, Product newProduct, int newProductWeight){
        int index = this.products.indexOf(oldProduct);
        if(index != -1){
            this.products.remove(index);
            this.weightOfProducts.remove(index);
            this.products.add(newProduct);
            this.weightOfProducts.add(newProductWeight);
        }
    }

    public List<Integer> getWeightOfProducts() {
        return weightOfProducts;
    }

    public void setWeightOfProducts(List<Integer> weightOfProducts) {
        this.weightOfProducts = weightOfProducts;
    }

    public void appendProduct(Product product, int weight){
        this.products.add(product);
        this.weightOfProducts.add(weight);
    }

    public void removeProduct(Product product){
        int index = this.products.indexOf(product);
        if(index != -1){
            this.products.remove(index);
            this.weightOfProducts.remove(index);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" +
                "mealId=" + mealId +
                ", name='" + name + '\'' +
                ", products=" + products +
                ", weightOfProducts=" + weightOfProducts +
                '}';
    }
}
