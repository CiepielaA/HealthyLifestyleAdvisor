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
        FrontEndProduct product1 = new FrontEndProduct(new Product("XXX", 666), 1);
        FrontEndProduct product2 = new FrontEndProduct(new Product("YYY", 777), 1);

        diaryDay.appendProduct(product1);
        diaryDay.appendProduct(product2);

        assertEquals(diaryDay.getFrontEndProducts().size(), 2);
        assertEquals(diaryDay.getFrontEndProducts().get(0), product1);
        assertEquals(diaryDay.getFrontEndProducts().get(1), product2);
    }

    @Test
    public void removeProduct() throws Exception {
        FrontEndProduct product1 = new FrontEndProduct(new Product("XXX", 666), 1);
        FrontEndProduct product2 = new FrontEndProduct(new Product("YYY", 777), 1);
        List<FrontEndProduct> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        diaryDay.setFrontEndProducts(products);

        diaryDay.removeProduct(product1);

        assertEquals(diaryDay.getFrontEndProducts().size(), 1);
        assertEquals(diaryDay.getFrontEndProducts().get(0), product2);
    }

    @Test
    public void doNothingWhenRemovingNonExistingProduct() throws Exception {
        FrontEndProduct product1 = new FrontEndProduct(new Product("XXX", 666), 1);
        FrontEndProduct product2 = new FrontEndProduct(new Product("YYY", 777), 1);
        List<FrontEndProduct> products = new ArrayList<>();
        products.add(product1);
        diaryDay.setFrontEndProducts(products);

        diaryDay.removeProduct(product2);

        assertEquals(diaryDay.getFrontEndProducts().size(), 1);
        assertEquals(diaryDay.getFrontEndProducts().get(0), product1);
    }

    @Test
    public void UpdateProduct() throws Exception {
        FrontEndProduct product1 = new FrontEndProduct(new Product("XXX", 666), 1);
        FrontEndProduct product2 = new FrontEndProduct(new Product("YYY", 777), 1);
        List<FrontEndProduct> products = new ArrayList<>();
        products.add(product1);
        diaryDay.setFrontEndProducts(products);

        diaryDay.updateProduct(product1, product2);

        assertEquals(diaryDay.getFrontEndProducts().size(), 1);
        assertEquals(diaryDay.getFrontEndProducts().get(0), product2);
    }

    @Test
    public void doNothingWhenUpdatingNonExistingProduct() throws Exception {
        FrontEndProduct product1 = new FrontEndProduct(new Product("XXX", 666), 1);
        FrontEndProduct product2 = new FrontEndProduct(new Product("YYY", 777), 1);
        List<FrontEndProduct> products = new ArrayList<>();
        products.add(product1);
        diaryDay.setFrontEndProducts(products);

        diaryDay.updateProduct(product2, product1);

        assertEquals(diaryDay.getFrontEndProducts().size(), 1);
        assertEquals(diaryDay.getFrontEndProducts().get(0), product1);
    }
}