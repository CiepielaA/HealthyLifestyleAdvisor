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

    //for jpa only
    private User() {
        this.diaryDays = new ArrayList<>();
    }

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.diaryDays = new ArrayList<>();
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


}
