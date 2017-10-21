package com.ciepiela.adrian.controllers;

import com.ciepiela.adrian.HealthyLifestyleAdvisorApplication;
import com.ciepiela.adrian.dao.UserRepository;
import com.ciepiela.adrian.model.User;
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
public class UserControllerTest {

    private static final String USER_LOGIN = "login";
    private static final String USER_PASSWORD = "password";
    private static final String USER_EMAIL = "email";
    private static final String NO_EXISTING_USER_EMAIL = "xxx";
    private static final String INVALID_JSON_USER = "xxx";
    private static int userId = 0;  //type int because of conversion types between mysql and java
    private static int noExistingUserId = 666;  //type int because of conversion types between mysql and java
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private User user;

    @Autowired
    private UserRepository userRepository;
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
        this.userRepository.deleteAllInBatch();
        this.user = userRepository.save(new User(USER_LOGIN, USER_PASSWORD, USER_EMAIL));
        userId++;
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                .content(convertToJson(user))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(userId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is(USER_LOGIN)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is(USER_PASSWORD)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(USER_EMAIL)));
    }

    @Test
    public void badRequestStatusWhenTryingToCreateUserFromInvalidData() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/create")
                .content(convertToJson(INVALID_JSON_USER))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete" + "/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void NotFoundStatusWhenTryingToDeleteNonExistingUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete" + "/" + noExistingUserId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void update() throws Exception {
        User updatedUser = new User("updatedLogin", "updatedPassword", "updatedEmail");

        mockMvc.perform(MockMvcRequestBuilders.post("/user/update" + "/" + userId)
                .content(convertToJson(updatedUser))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(userId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is("updatedLogin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("updatedPassword")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("updatedEmail")));
    }

    @Test
    public void NotFoundStatusWhenTryingToUpdateNonExistingUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update" + "/" + noExistingUserId)
                .content(convertToJson(user))
                .contentType(contentType))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void findById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/findById" + "/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(userId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is(USER_LOGIN)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is(USER_PASSWORD)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(USER_EMAIL)));
    }

    @Test
    public void userNotFoundById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/findById" + "/" + noExistingUserId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void findByEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/findByEmail" + "/" + USER_EMAIL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(contentType))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(userId)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.login", Matchers.is(USER_LOGIN)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is(USER_PASSWORD)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is(USER_EMAIL)));
    }

    @Test
    public void userNotFoundByEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/findByEmail" + "/" + NO_EXISTING_USER_EMAIL))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private String convertToJson(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}