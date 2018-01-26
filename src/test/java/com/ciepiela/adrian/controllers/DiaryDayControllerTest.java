package com.ciepiela.adrian.controllers;

import com.ciepiela.adrian.HealthyLifestyleAdvisorApplication;
import com.ciepiela.adrian.dao.DiaryDayRepository;
import com.ciepiela.adrian.dao.ProductRepository;
import com.ciepiela.adrian.dao.UserRepository;
import com.ciepiela.adrian.model.DiaryDay;
import com.ciepiela.adrian.model.Product;
import com.ciepiela.adrian.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HealthyLifestyleAdvisorApplication.class)
@WebAppConfiguration
public class DiaryDayControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mockMvc;
    private DiaryDay diaryDay;
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final int DIARY_DAY_ID = 1;
    private static final String INVALID_JSON_DIARY_DAY = "xxx";
    private static int noExistingDiaryDayId = 666;

    @Autowired
    private DiaryDayRepository diaryDayRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;

//    @Autowired
//    void setConverters(HttpMessageConverter<?>[] converters) {
//        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
//                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
//                .findAny()
//                .orElse(null);
//        Assert.assertNotNull("the JSON message converter must not be null",
//                this.mappingJackson2HttpMessageConverter);
//    }

    @Before
    public void setUp() throws Exception {
        objectMapper.findAndRegisterModules();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.diaryDayRepository.deleteAllInBatch();
        DiaryDay diaryDay = new DiaryDay();
        this.diaryDay = diaryDayRepository.save(diaryDay);
    }

    @Test
    public void create() throws Exception {
        Product product = new Product("abc", 666);
//        Product product2 = new Product("abc", 666);
        productRepository.save(product);
//        productRepository.save(product2);
//        diaryDay.setProducts(Arrays.asList(product, product2));
        diaryDay.setProducts(Collections.singletonList(product));

        mockMvc.perform(MockMvcRequestBuilders.post("/diaryDay/create")
                .content(convertToJson(diaryDay))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.diaryDayId", Matchers.is(DIARY_DAY_ID)));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is(diaryDay.getDate())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0]", Matchers.is(diaryDay.getProducts().get(0))));
    }

    @Test
    public void badRequestStatusWhenTryingToCreateDiaryDayFromInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/diaryDay/create")
                .content(convertToJson(INVALID_JSON_DIARY_DAY))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/diaryDay/delete" + "/" + diaryDay.getDiaryDayId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void NotFoundStatusWhenTryingToDeleteNonExistingUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/diaryDay/delete" + "/" + noExistingDiaryDayId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void update() throws Exception {
        Product product = new Product("cba", 999);
        productRepository.save(product);
        diaryDay.setProducts(Collections.singletonList(product));


        mockMvc.perform(MockMvcRequestBuilders.post("/diaryDay/update" + "/" + diaryDay.getDiaryDayId())
                .content(convertToJson(diaryDay))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.diaryDayId", Matchers.is(DIARY_DAY_ID)));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is(diaryDay.getDate())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0]", Matchers.is(diaryDay.getProducts().get(0))));

    }

    @Test
    public void appendNewProductToList() throws Exception {
        Product product = new Product("cba", 999);
        productRepository.save(product);

        this.diaryDay.appendProduct(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/diaryDay/update" + "/" + diaryDay.getDiaryDayId())
                .content(convertToJson(diaryDay))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.diaryDayId", Matchers.is(DIARY_DAY_ID)));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is(diaryDay.getDate())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0]", Matchers.is(diaryDay.getProducts().get(0))));
    }

    @Test
    public void deleteProductFromList() throws Exception {
        Product product = new Product("abc", 666);
        Product product2 = new Product("cba", 777);
        productRepository.save(product);
        productRepository.save(product2);
        diaryDay.appendProduct(product);
        diaryDay.appendProduct(product2);
        diaryDayRepository.save(diaryDay);
        diaryDay.removeProduct(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/diaryDay/create")
                .content(convertToJson(diaryDay))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.diaryDayId", Matchers.is(DIARY_DAY_ID)));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is(diaryDay.getDate())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0]", Matchers.is(diaryDay.getProducts().get(0))));
    }

    @Test
    public void NotFoundStatusWhenTryingToUpdateNonExistingUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/diaryDay/update" + "/" + noExistingDiaryDayId)
                .content(convertToJson(diaryDay))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void findById() throws Exception {
        Product product = new Product("abc", 666);
        productRepository.save(product);
        diaryDay.setProducts(Collections.singletonList(product));
        diaryDayRepository.save(diaryDay);

        mockMvc.perform(MockMvcRequestBuilders.get("/diaryDay/findById" + "/" + diaryDay.getDiaryDayId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.diaryDayId", Matchers.is((int)diaryDay.getDiaryDayId())));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is(diaryDay.getDate())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0]", Matchers.is(diaryDay.getProducts().get(0))));
    }

    @Test
    public void findByDateAndUserId() throws Exception {
        User user = new User("login", "password", "email");
        diaryDay.setUser(user);
        userRepository.save(user);
        diaryDayRepository.save(diaryDay);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/diaryDay/findByDateAndUserId" + "/" + diaryDay.getDate() + "/" + diaryDay.getDiaryDayId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.diaryDayId", Matchers.is((int)diaryDay.getDiaryDayId())));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is(diaryDay.getDate())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.user", Matchers.is(diaryDay.getUser())));
    }

    private String convertToJson(Object o) throws IOException {
        return objectMapper.writeValueAsString(o);
    }

}