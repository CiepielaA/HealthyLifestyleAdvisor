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
    private String date;

    @OneToMany
    @Column(name = "diary_day_id")
    private List<FrontEndProduct> frontEndProducts = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
//    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    public DiaryDay() {
        date = LocalDate.now().toString();
    }

    public long getDiaryDayId() {
        return diaryDayId;
    }

//    public LocalDate getDate() {
//        return date;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<FrontEndProduct> getFrontEndProducts() {
        return frontEndProducts;
    }

    public void setFrontEndProducts(List<FrontEndProduct> frontEndProducts) {
        this.frontEndProducts = frontEndProducts;
    }

    public void appendProduct(FrontEndProduct product) {
        this.getFrontEndProducts().add(product);
    }

    public void removeProduct(FrontEndProduct product) {
        this.getFrontEndProducts().remove(product);
    }

    public void updateProduct(FrontEndProduct oldProduct, FrontEndProduct newProduct){
        int index = this.frontEndProducts.indexOf(oldProduct);
        if(index != -1){
            this.frontEndProducts.remove(index);
            this.frontEndProducts.add(newProduct);
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
                ", frontEndProducts=" + frontEndProducts +
                ", user=" + user +
                '}';
    }
}
