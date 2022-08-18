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
public class EventControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getEventByIdTest() throws Exception {
        this.mockMvc.perform(get("/events/find?id=1")).andDo(print())
                .andExpect(view().name("events/event"));
    }

    @Test
    public void getEventsByTitleTest() throws Exception {
        this.mockMvc.perform(get("/events?title=Dance&pageSize=1&pageNum=1")).andDo(print())
                .andExpect(view().name("events/event"));
    }

    @Test
    public void createEventTest() throws Exception {
        this.mockMvc.perform(post("/events?title=Dance&date=1616-12-15")).andDo(print())
                .andExpect(view().name("events/event"));
    }

    @Test
    public void updateEventTest() throws Exception {
        this.mockMvc.perform(put("/events?id=1&title=Dance&date=2020-12-15")).andDo(print())
                .andExpect(view().name("events/event"));
    }

    @Test
    public void deleteEventTest() throws Exception {
        this.mockMvc.perform(delete("/events?id=1")).andDo(print())
                .andExpect(view().name("events/event"));
    }
}
