package com.ciepiela.adrian.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("Duplicates")
public class DiaryDayTest {
    private DiaryDay diaryDay;

    @Before
    public void setUp(){
        diaryDay = new DiaryDay();
    }

    @Test
    public void appendProduct() throws Exception {
        Product product1 = new Product("XXX", 666);
        Product product2 = new Product("YYY", 777);

        diaryDay.appendProduct(product1);
        diaryDay.appendProduct(product2);

        assertEquals(diaryDay.getProducts().size(), 2);
        assertEquals(diaryDay.getProducts().get(0), product1);
        assertEquals(diaryDay.getProducts().get(1), product2);
    }

    @Test
    public void removeProduct() throws Exception {
        Product product1 = new Product("XXX", 666);
        Product product2 = new Product("YYY", 777);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        diaryDay.setProducts(products);

        diaryDay.removeProduct(product1);

        assertEquals(diaryDay.getProducts().size(), 1);
        assertEquals(diaryDay.getProducts().get(0), product2);
    }

    @Test
    public void doNothingWhenRemovingNonExistingProduct() throws Exception {
        Product product1 = new Product("XXX", 666);
        Product product2 = new Product("YYY", 777);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        diaryDay.setProducts(products);

        diaryDay.removeProduct(product2);

        assertEquals(diaryDay.getProducts().size(), 1);
        assertEquals(diaryDay.getProducts().get(0), product1);
    }

    @Test
    public void UpdateProduct() throws Exception {
        Product product1 = new Product("XXX", 666);
        Product product2 = new Product("YYY", 777);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        diaryDay.setProducts(products);

        diaryDay.updateProduct(product1, product2);

        assertEquals(diaryDay.getProducts().size(), 1);
        assertEquals(diaryDay.getProducts().get(0), product2);
    }

    @Test
    public void doNothingWhenUpdatingNonExistingProduct() throws Exception {
        Product product1 = new Product("XXX", 666);
        Product product2 = new Product("YYY", 777);
        List<Product> products = new ArrayList<>();
        products.add(product1);
        diaryDay.setProducts(products);

        diaryDay.updateProduct(product2, product1);

        assertEquals(diaryDay.getProducts().size(), 1);
        assertEquals(diaryDay.getProducts().get(0), product1);
    }
}