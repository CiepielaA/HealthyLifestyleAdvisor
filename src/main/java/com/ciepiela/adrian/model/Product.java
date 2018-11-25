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

    //for jpa only
    public Product() {
    }

    public Product(Product product) {
        this.description = product.getDescription();
        this.kcal = product.getKcal();
        this.proteins = product.getProteins();
        this.fats = product.getFats();
        this.carbs = product.getCarbs();
    }

    public Product(String description, int kcal) {
        this(description, kcal, 0, 0, 0);
    }



    public Product(String description, int proteins, int fats, int carbs) {
        this(description, 4* proteins + 9* fats + 4*carbs, proteins, fats, carbs);
    }

    public Product(String description, int kcal, int proteins, int fat, int carbs) {
        this.description = description;
        this.kcal = kcal;
        this.proteins = proteins;
        this.fats = fat;
        this.carbs = carbs;
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


    @Override
    public String toString() {
        return "{" +
                "description=" + description +
                ", kcal=" + kcal +
                ", proteins=" + proteins +
                ", fats=" + fats +
                ", carbs=" + carbs +
                ", id=" + productId +
                '}';
    }
}

