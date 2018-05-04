package com.ciepiela.adrian.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Goals {
    @Column(nullable = true)
    private int kcal;
    @Column(nullable = true)
    private int proteins;
    @Column(nullable = true)
    private int carbs;
    @Column(nullable = true)
    private int fats;

    public Goals(int proteins, int carbs, int fats) {
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
        this.kcal = 4*proteins + 9*fats + 4*carbs;
    }

    public Goals(int kcal) {
        this.kcal = kcal;
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
        this.kcal = 4*proteins + 9*fats + 4*carbs;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
        this.kcal = 4*proteins + 9*fats + 4*carbs;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
        this.kcal = 4*proteins + 9*fats + 4*carbs;
    }
}
