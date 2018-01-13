package com.ciepiela.adrian.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DiaryDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_day_id")
    private long diaryDayId;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany
    @Column(name = "diary_day_id")
    private List<Product> products = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
//    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    public DiaryDay() {
        date = LocalDate.now();
    }

    public long getDiaryDayId() {
        return diaryDayId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void appendProduct(Product product) {
        this.getProducts().add(product);
    }

    public void removeProduct(Product product) {
        this.getProducts().remove(product);
    }

    public void updateProduct(Product oldProduct, Product newProduct){
        int index = this.products.indexOf(oldProduct);
        if(index != -1){
            this.products.remove(index);
            this.products.add(newProduct);
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
                "diaryDayId=" + diaryDayId +
                ", date=" + date +
                ", products=" + products +
                ", user=" + user +
                '}';
    }
}
