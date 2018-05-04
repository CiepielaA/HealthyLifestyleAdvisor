package com.ciepiela.adrian.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(nullable = false)
    private String login;

//    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany
    @Column(name = "user_id")
    private List<DiaryDay> diaryDays;

    @OneToMany
    @Column(name = "user_id")
    private List<Meal> meals;

    @Embedded
    private Goals goals;

    //for jpa only
    private User() {
        this.diaryDays = new ArrayList<>();
        this.meals = new ArrayList<>();
    }

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.diaryDays = new ArrayList<>();
        this.meals = new ArrayList<>();
    }

    public long getUserId() {
        return userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<DiaryDay> getDiaryDays() {
        return diaryDays;
    }

    public void setDiaryDays(List<DiaryDay> diaryDays) {
        this.diaryDays = diaryDays;
    }

    public void appendDiaryDay(DiaryDay diaryDay) {
        this.diaryDays.add(diaryDay);
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public void appendMeal(Meal meal) {
        this.meals.add(meal);
    }

    public void removeMeal(Meal meal){
        int index = this.meals.indexOf(meal);
        if(index != -1){
            this.meals.remove(index);
        }
    }

//    public Goals getGoals() {
//        return goals;
//    }
//
//    public void setGoals(Goals goals) {
//        this.goals = goals;
//    }
}
