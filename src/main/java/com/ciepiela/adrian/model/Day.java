package com.ciepiela.adrian.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_id")
    private long dayId;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany
    @Column(name = "day_id")
    private List<Product> products;

    public Day() {}

    public long getDayId() {
        return dayId;
    }

    public void setDayId(long dayId) {
        this.dayId = dayId;
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
}
