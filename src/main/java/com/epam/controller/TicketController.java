package com.epam.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.entity.Event;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.facade.BookingFacade;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private BookingFacade facade;

    @PostMapping
    public String bookTicket(@RequestParam long userId, @RequestParam long eventId, @RequestParam int place,
                             @RequestParam("category") String categoryString, @RequestParam("cost")
                             BigDecimal cost, Model model) {
        logger.info("Handle a request to book a ticket");
        Ticket.Category category = Ticket.Category.valueOf(categoryString);
        Ticket ticket = facade.bookTicket(userId, eventId, place, category, cost);
        if (ticket == null) {
            model.addAttribute("result", "Nothing");
        } else {
            model.addAttribute("tickets", new Ticket[] {ticket});
        }
        return "tickets/ticket";
    }

    @GetMapping("/byUser")
    public String getBookedTicketsByUserId(@RequestParam long userId, @RequestParam int pageSize,
                                           @RequestParam int pageNum, Model model) {
        logger.info("Handle a request to find a ticket by user id");
        User user = new User(userId);
        List<Ticket> ticketList = facade.getBookedTickets(user, pageSize, pageNum);
        if (ticketList == null) {
            model.addAttribute("result", "Nothing");
        } else {
            model.addAttribute("tickets", ticketList);
        }
        return "tickets/ticket";
    }

    @GetMapping("/byEvent")
    public String getBookedTicketsByEventId(@RequestParam long eventId, @RequestParam int pageSize,
                                            @RequestParam int pageNum, Model model) {
        logger.info("Handle a request to find a ticket by event id");
        Event event = new Event();
        event.setId(eventId);
        List<Ticket> ticketList = facade.getBookedTickets(event, pageSize, pageNum);
        if (ticketList == null) {
            model.addAttribute("result", "Nothing");
        } else {
            model.addAttribute("tickets", ticketList);
        }
        return "tickets/ticket";
    }

    @DeleteMapping
    public String cancelTicket(@RequestParam long ticketId, Model model) {
        logger.info("Handle a request to delete a ticket");
        facade.cancelTicket(ticketId);
        model.addAttribute("result", true);
        return "tickets/ticket";
    }
}
