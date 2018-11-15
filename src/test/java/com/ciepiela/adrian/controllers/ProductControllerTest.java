package com.ciepiela.adrian.controllers;

import com.ciepiela.adrian.HealthyLifestyleAdvisorApplication;
import com.ciepiela.adrian.dao.ProductRepository;
import com.ciepiela.adrian.model.Product;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthyLifestyleAdvisorApplication.class)
@WebAppConfiguration
public class ProductControllerTest {

    private static final String DESCRIPTION = "description";
    private static final String NO_EXISTING_DESCRIPTION = "xxx";
    private static final int KCAL = 500;
    private static final int PROTEIN = 10;
    private static final int FAT = 20;
    private static final int CARBS = 30;
    private static final int ALCOHOL = 40;
    private static final int NO_EXISTING_PRODUCT_ID = 0;
    private static final String INVALID_JSON_PRODUCT = "xxx";
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private Product product;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.productRepository.deleteAllInBatch();
    }

    @Test
    public void create() throws Exception {
        this.product = new Product(DESCRIPTION, KCAL);
        mockMvc.perform(MockMvcRequestBuilders.post("/product/create")
                .content(convertToJson(product))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(DESCRIPTION)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.kcal", Matchers.is(KCAL)));

        this.product = new Product(DESCRIPTION, PROTEIN, FAT, CARBS);
        mockMvc.perform(MockMvcRequestBuilders.post("/product/create")
                .content(convertToJson(product))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(DESCRIPTION)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.proteins", Matchers.is(PROTEIN)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fats", Matchers.is(FAT)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carbs", Matchers.is(CARBS)));

        this.product = new Product(DESCRIPTION, PROTEIN, FAT, CARBS, ALCOHOL);
        mockMvc.perform(MockMvcRequestBuilders.post("/product/create")
                .content(convertToJson(product))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(DESCRIPTION)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.proteins", Matchers.is(PROTEIN)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fats", Matchers.is(FAT)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carbs", Matchers.is(CARBS)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.alcohol", Matchers.is(ALCOHOL)));
    }

    @Test
    public void badRequestStatusWhenTryingToCreateProductFromInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/product/create")
                .content(convertToJson(INVALID_JSON_PRODUCT))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void delete() throws Exception {
        this.product = productRepository.save(new Product(DESCRIPTION, KCAL));
        mockMvc.perform(MockMvcRequestBuilders.get("/product/deleteById" + "/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void NotFoundStatusWhenTryingToDeleteNonExistingProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/deleteById" + "/" + NO_EXISTING_PRODUCT_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void update() throws Exception {
        this.product = productRepository.save(new Product(DESCRIPTION, PROTEIN, FAT, CARBS, ALCOHOL));
        Product updatedProduct = new Product("updated description", 111, 222, 333,444);

        mockMvc.perform(MockMvcRequestBuilders.post("/product/updateById" + "/" + product.getId())
                .content(convertToJson(updatedProduct))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is((int)product.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(updatedProduct.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.proteins", Matchers.is(updatedProduct.getProteins())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fats", Matchers.is(updatedProduct.getFats())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carbs", Matchers.is(updatedProduct.getCarbs())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.alcohol", Matchers.is(updatedProduct.getAlcohol())));
    }

    @Test
    public void NotFoundStatusWhenTryingToUpdateNonExistingProduct() throws Exception {
        this.product = new Product(DESCRIPTION, KCAL);
        mockMvc.perform(MockMvcRequestBuilders.post("/product/updateById" + "/" + NO_EXISTING_PRODUCT_ID)
                .content(convertToJson(product))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void findById() throws Exception {
        this.product = productRepository.save(new Product(DESCRIPTION, PROTEIN, FAT, CARBS, ALCOHOL));
        mockMvc.perform(MockMvcRequestBuilders.get("/product/findById" + "/" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is((int) product.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(product.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.proteins", Matchers.is(product.getProteins())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fats", Matchers.is(product.getFats())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carbs", Matchers.is(product.getCarbs())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.alcohol", Matchers.is(product.getAlcohol())));
    }

    @Test
    public void userNotFoundById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/findById" + "/" + NO_EXISTING_PRODUCT_ID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }


    @Test
    public void findByDescription() throws Exception {
        this.product = productRepository.save(new Product(DESCRIPTION, PROTEIN, FAT, CARBS, ALCOHOL));
        mockMvc.perform(MockMvcRequestBuilders.get("/product/findByDescription" + "/" + product.getDescription()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is((int) product.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(product.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.proteins", Matchers.is(product.getProteins())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fats", Matchers.is(product.getFats())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.carbs", Matchers.is(product.getCarbs())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.alcohol", Matchers.is(product.getAlcohol())));
    }

    @Test
    public void userNotFoundByDescription() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/findByDescription" + "/" + NO_EXISTING_DESCRIPTION))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private String convertToJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}