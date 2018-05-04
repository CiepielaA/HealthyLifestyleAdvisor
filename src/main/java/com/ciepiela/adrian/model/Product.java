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
    private int kcal;
    private int proteins;
    private int fats;
    private int carbs;
    private int alcohol;

    //for jpa only
    private Product() {
    }

    public Product(String description, int kcal) {
        this(description, kcal, 0, 0, 0, 0);
    }

    public Product(String description, int proteins, int fats, int carbs) {
        this(description, proteins, fats, carbs, 0);
    }

    public Product(String description, int proteins, int fats, int carbs, int alcohol) {
        this(description, 4* proteins + 9* fats + 4*carbs + 7*alcohol, proteins, fats, carbs, alcohol);
    }

    private Product(String description, int kcal, int proteins, int fat, int carbs, int alcohol) {
        this.description = description;
        this.kcal = kcal;
        this.proteins = proteins;
        this.fats = fat;
        this.carbs = carbs;
        this.alcohol = alcohol;
    }

    public long getId() {
        return productId;
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

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(int alcohol) {
        this.alcohol = alcohol;
    }

    @Override
    public String toString() {
        return "{" +
                "description=" + description +
                ", kcal=" + kcal +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", carbs=" + carbs +
                ", alcohol=" + alcohol +
                ", id=" + productId +
                '}';
    }
}

