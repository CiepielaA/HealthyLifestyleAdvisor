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
    private int protein;
    private int fat;
    private int carbs;
    private int alcohol;

    //for jpa only
    private Product() {
    }

    public Product(String description, int kcal) {
        this(description, kcal, 0, 0, 0, 0);
    }

    public Product(String description, int protein, int fat, int carbs) {
        this(description, protein, fat, carbs, 0);
    }

    public Product(String description, int protein, int fat, int carbs, int alcohol) {
        this(description, 4*protein + 9*fat + 4*carbs, protein, fat, carbs, alcohol);
    }

    private Product(String description, int kcal, int protein, int fat, int carbs, int alcohol) {
        this.description = description;
        this.kcal = kcal;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.alcohol = alcohol;
    }

//    public static Product createProductWithKcal(String description, int kcal){
//        return new Product(description, kcal, 0, 0, 0, 0);
//    }
//
//    public static Product createProductWithMacronutrients(String description, int protein, int fat, int carbs) {
//        int kcal = calculateAmountOfKcal(protein, fat, carbs);
//        return new Product(description, kcal, protein, fat, carbs,  0);
//    }
//
//    public static Product createProductWithAlcohol(String description, int protein, int fat, int carbs, int alcohol) {
//        int kcal = calculateAmountOfKcal(protein, fat, carbs, alcohol);
//        return new Product(description, kcal, protein, fat, carbs,  alcohol);
//    }

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

    public int getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(int alcohol) {
        this.alcohol = alcohol;
    }

//    private int calculateAmountOfKcal(int protein, int fat, int carbs) {
//        return 4*protein + 9*fat + 4*carbs;
//    }
//
//    private int calculateAmountOfKcal(int protein, int fat, int carbs, int alcohol) {
//        return 4*protein + 9*fat + 4*carbs + 7*alcohol;
//    }
}

