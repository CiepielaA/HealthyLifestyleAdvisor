package com.ciepiela.adrian.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import static org.apache.commons.lang3.math.NumberUtils.isParsable;

@Entity
public class FrontEndProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "front_end_product_id")
    private long frontEndProductId;
    @OneToOne
    private Product frontEndProduct;
    @OneToOne
    private Product product;
    private int weight;
    private int kcal;

    public FrontEndProduct() {
    }

    public FrontEndProduct(Product product, int weight) {
        this.product = product;
        this.weight = weight;
        this.frontEndProduct = product;
    }

    public long getFrontEndProductId() {
        return frontEndProductId;
    }

    public Product getProduct() {
        return frontEndProduct;
    }

    public void setProduct(Product product) {
        this.frontEndProduct = product;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return this.frontEndProduct.getDescription();
    }

    public int getKcal() {
        return kcal;
    }

    public int getProteins() {
        return this.frontEndProduct.getProteins();
    }
    public int getFats() {
        return this.frontEndProduct.getFats();
    }

    public int getCarbs() {
        return this.frontEndProduct.getCarbs();
    }

}
