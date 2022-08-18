package com.epam.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class TicketControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void bookTicket() throws Exception {
        mockMvc.perform(post("/tickets?userId=1&eventId=1&place=1&category=BAR&cost=10")).andDo(print())
                .andExpect(view().name("tickets/ticket"));
    }

    @Test
    public void getBookedTicketsByUserId() throws Exception {
        mockMvc.perform(get("/tickets/byUser?userId=1&pageSize=1&pageNum=1")).andDo(print())
                .andExpect(view().name("tickets/ticket"));
    }

    @Test
    public void getBookedTicketsByEventId() throws Exception {
        mockMvc.perform(get("/tickets/byEvent?eventId=1&pageSize=1&pageNum=1")).andDo(print())
                .andExpect(view().name("tickets/ticket"));
    }

    @Test
    public void cancelTicket() throws Exception {
        mockMvc.perform(delete("/tickets?ticketId=1")).andDo(print())
                .andExpect(view().name("tickets/ticket"));
    }
}
