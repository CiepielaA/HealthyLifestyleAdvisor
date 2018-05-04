package com.ciepiela.adrian.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class FrontEndProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "front_end_product_id")
    private long frontEndProductId;
    @OneToOne
    private Product product;
    private float weight;

    //for jpa only
    private FrontEndProduct() {
    }

    public FrontEndProduct(Product product, int weight) {
        this.product = product;
        this.weight = weight;
    }

    public long getFrontEndProductId() {
        return frontEndProductId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return this.product.getDescription();
    }

    public void setDescription(String description) {
        this.product.setDescription(description);
    }

    public int getKcal() {
        return this.product.getKcal();
    }

    public void setKcal(int kcal) {
        this.product.setKcal(kcal);
    }

    public int getProtein() {
        return this.product.getProtein();
    }

    public void setProtein(int protein) {
        this.product.setProtein(protein);
    }

    public int getFat() {
        return this.product.getFat();
    }

    public void setFat(int fat) {
        this.product.setFat(fat);
    }

    public int getCarbs() {
        return this.product.getCarbs();
    }

    public void setCarbs(int carbs) {
        this.product.setCarbs(carbs);
    }

    public int getAlcohol() {
        return this.product.getAlcohol();
    }

    public void setAlcohol(int alcohol) {
        this.product.setAlcohol(alcohol);
    }

    @Override
    public String toString() {
        return "FrontEndProduct{" +
            "frontEndProductId=" + frontEndProductId +
            ", product=" + product +
            ", weight=" + weight +
            '}';
    }
}
