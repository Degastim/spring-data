package com.epam.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.entity.Event;
import com.epam.facade.BookingFacade;

@Controller
@RequestMapping("/events")
public class EventController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private BookingFacade facade;

    @GetMapping("/find")
    public String getEventById(@RequestParam long id, Model model) {
        logger.info("Handle a request to find an event by ID");
        Event event = facade.getEventById(id);
        if (event == null) {
            model.addAttribute("result", "Nothing");
        } else {
            model.addAttribute("events", new Event[] {event});
        }
        return "events/event";
    }

    @GetMapping
    public String getEventsByTitle(@RequestParam String title, @RequestParam int pageSize, @RequestParam int pageNum,
                                   Model model) {
        logger.info("Handle a request to find an event by title");
        List<Event> eventList = facade.getEventsByTitle(title, pageSize, pageNum);
        if (eventList == null) {
            model.addAttribute("result", "Nothing");
        } else {
            model.addAttribute("events", eventList);
        }
        return "events/event";
    }

    @PostMapping
    public String createEvent(@RequestParam String title,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date, Model model) {
        logger.info("Handle a request to create an event");
        Event event = new Event(title, date);
        event = facade.createEvent(event);
        if (event == null) {
            model.addAttribute("result", "Nothing");
        } else {
            model.addAttribute("events", new Event[] {event});
        }
        return "events/event";
    }

    @PutMapping
    public String updateEvent(@RequestParam long id, @RequestParam String title,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                              Model model) {
        logger.info("Handle a request to update an event");
        Event event = new Event(id, title, date);
        event = facade.updateEvent(event);
        if (event == null) {
            model.addAttribute("result", "Nothing");
        } else {
            model.addAttribute("events", new Event[] {event});
        }
        return "events/event";
    }

    @DeleteMapping
    public String deleteEvent(@RequestParam long id, Model model) {
        logger.info("Handle a request to delete an event");
        facade.deleteEvent(id);
        model.addAttribute("result", true);
        return "events/event";
    }
}
