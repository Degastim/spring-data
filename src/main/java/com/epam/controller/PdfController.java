package com.epam.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.entity.Event;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.facade.BookingFacade;

@Controller
@RequestMapping("/pdf")
public class PdfController {
    private static final Logger logger = LogManager.getLogger();
    private final BookingFacade facade;
    private final PdfCreator pdfCreator;

    @Autowired
    PdfController(BookingFacade facade, PdfCreator pdfCreator) {
        this.facade = facade;
        this.pdfCreator = pdfCreator;
    }

    @GetMapping("/byUser")
    public String getBookedTicketsByUserId(@RequestParam long userId, @RequestParam int pageSize,
                                           @RequestParam int pageNum, HttpServletResponse response)
            throws IOException {
        logger.info("Handle a request to find tickets by user id");
        User user = new User();
        user.setId(userId);
        List<Ticket> ticketList = facade.getBookedTickets(user, pageSize, pageNum);
        pdfCreator.printTicketsPdf(ticketList, response);
        return "index";
    }

    @GetMapping("/byEvent")
    public String getBookedTicketsByEventId(@RequestParam long eventId, @RequestParam int pageSize,
                                            @RequestParam int pageNum, HttpServletResponse response)
            throws IOException {
        logger.info("Handle a request to find tickets by event id");
        Event event = new Event(eventId);
        List<Ticket> ticketList = facade.getBookedTickets(event, pageSize, pageNum);
        pdfCreator.printTicketsPdf(ticketList, response);
        return "index";
    }
}
