package com.ciepiela.adrian.model;

import javax.persistence.*;
import java.time.LocalDate;
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
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    //for jpa only
    private DiaryDay() {}

    public long getDiaryDayId() {
        return diaryDayId;
    }

    public void setDiaryDayId(long diaryDayId) {
        this.diaryDayId = diaryDayId;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
