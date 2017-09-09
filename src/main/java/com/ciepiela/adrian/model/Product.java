package com.ciepiela.adrian.model;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long productId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int kcal;

    @Column(nullable = false)
    private int protein;

    @Column(nullable = false)
    private int fat;

    @Column(nullable = false)
    private int carbs;

    public Product() {
    }

    public long getId() {
        return productId;
    }

    public void setId(long id) {
        this.productId = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }
}

