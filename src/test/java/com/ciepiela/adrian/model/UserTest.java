package com.ciepiela.adrian.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UserTest {

    private User user;

    @Before
    public void setUp(){
        user = new User("login", "password", "email");
    }

    @Test
    public void appendMeal() throws Exception {
        Meal meal1 = new Meal("XXX");
        Meal meal2 = new Meal("XXX");

        user.appendMeal(meal1);
        user.appendMeal(meal2);

        Assert.assertEquals(user.getMeals().size(), 2);
        Assert.assertEquals(user.getMeals().get(0), meal1);
        Assert.assertEquals(user.getMeals().get(1), meal2);
    }

    @Test
    public void removeMeal() throws Exception {
        Meal meal1 = new Meal("XXX");
        Meal meal2 = new Meal("XXX");
        List<Meal> meals = new ArrayList<>();
        meals.add(meal1);
        meals.add(meal2);
        user.setMeals(meals);

        user.removeMeal(meal1);

        Assert.assertEquals(user.getMeals().size(), 1);
        Assert.assertEquals(user.getMeals().get(0), meal2);
    }

}