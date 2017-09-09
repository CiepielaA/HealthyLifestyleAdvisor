package com.ciepiela.adrian.model;

import javax.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String email;


}
