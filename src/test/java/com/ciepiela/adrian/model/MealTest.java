package com.ciepiela.adrian.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.naming.directory.InvalidAttributesException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("Duplicates")
public class MealTest {

    private Meal meal;

    @Before
    public void setUp(){
        meal = new Meal("MEAL");
    }

    @Test
    public void appendProduct() throws Exception {
        Product product1 = new Product("XXX", 666);
        Product product2 = new Product("YYY", 777);

        meal.appendProduct(product1, 1);
        meal.appendProduct(product2, 2);

        Assert.assertEquals(meal.getProducts().size(), 2);
        Assert.assertEquals(meal.getWeightOfProducts().size(), 2);
        Assert.assertEquals(meal.getProducts().get(0), product1);
        Assert.assertEquals(meal.getProducts().get(1), product2);
    }

    @Test
    public void removeProduct() throws Exception {
        Product product1 = new Product("XXX", 666);
        Product product2 = new Product("YYY", 777);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        List<Integer> weights = new ArrayList<>();
        weights.add(1);
        weights.add(2);
        meal.setProducts(products, weights);

        meal.removeProduct(product1);

        Assert.assertEquals(meal.getProducts().size(), 1);
        Assert.assertEquals(meal.getWeightOfProducts().size(), 1);
        Assert.assertEquals(meal.getProducts().get(0), product2);
        Assert.assertEquals(meal.getWeightOfProducts().get(0), new Integer(2));
    }

    @Test
    public void UpdateProduct() throws Exception {
        Product product1 = new Product("XXX", 666);
        Product product2 = new Product("YYY", 777);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        List<Integer> weights = new ArrayList<>();
        weights.add(1);
        meal.setProducts(products, weights);

        meal.updateProduct(product1, product2,2);

        assertEquals(meal.getProducts().size(), 1);
        assertEquals(meal.getProducts().get(0), product2);
        assertEquals(meal.getWeightOfProducts().get(0), new Integer(2));
    }

    @Test
    public void doNothingWhenRemovingNonExistingProduct() throws Exception {
        Product product1 = new Product("XXX", 666);
        Product product2 = new Product("YYY", 777);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        List<Integer> weights = new ArrayList<>();
        weights.add(1);
        meal.setProducts(products, weights);

        meal.removeProduct(product2);

        assertEquals(meal.getProducts().size(), 1);
        assertEquals(meal.getProducts().get(0), product1);
        assertEquals(meal.getWeightOfProducts().get(0), new Integer(1));
    }

    @Test(expected = InvalidAttributesException.class)
    public void throwExceptionWhenNumberOfProductsAndWeightsOfProductsIsDifferent() throws Exception {
        Product product1 = new Product("XXX", 666);
        Product product2 = new Product("YYY", 777);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        List<Integer> weights = new ArrayList<>();
        weights.add(1);

        meal.setProducts(products, weights);
    }

    @Test
    public void doNothingWhenUpdatingNonExistingProduct() throws Exception {
        Product product1 = new Product("XXX", 666);
        Product product2 = new Product("YYY", 777);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        List<Integer> weights = new ArrayList<>();
        weights.add(1);
        meal.setProducts(products, weights);

        meal.updateProduct(product2, product1, 1);

        assertEquals(meal.getProducts().size(), 1);
        assertEquals(meal.getProducts().get(0), product1);
        assertEquals(meal.getWeightOfProducts().get(0), new Integer(1));
    }

}