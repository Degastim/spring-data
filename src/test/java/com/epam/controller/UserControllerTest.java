package com.epam.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/applicationContextMVC.xml")
@WebAppConfiguration
public class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getUserById() throws Exception {
        this.mockMvc.perform(get("/users/find?id=1")).andDo(print())
                .andExpect(view().name("users/user"));
    }

    @Test
    public void getUserByEmail() throws Exception {
        this.mockMvc.perform(get("/users/findByEmail?email=jon@gmail.com")).andDo(print())
                .andExpect(view().name("users/user"));
    }

    @Test
    public void getUsersByName() throws Exception {
        this.mockMvc.perform(get("/users/findByUsername?name=Jon&pageSize=1&pageNum=1")).andDo(print())
                .andExpect(view().name("users/user"));
    }

    @Test
    public void createUser() throws Exception {
        this.mockMvc.perform(post("/users?name=Anna&email=anna@gmail.com")).andDo(print())
                .andExpect(view().name("users/user"));
    }

    @Test
    public void updateUser() throws Exception {
        this.mockMvc.perform(put("/users?id=1&name=Denis&email=denis@gmail.com")).andDo(print())
                .andExpect(view().name("users/user"));
    }

    @Test
    public void deleteUser() throws Exception {
        this.mockMvc.perform(delete("/users?id=1")).andDo(print())
                .andExpect(view().name("users/user"));
    }
}
