package com.epam.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.dao.EventRepository;
import com.epam.entity.Event;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventService {
    private static final Logger logger = LogManager.getLogger();
    private final EventRepository eventRepository;

    public Event getEventById(long eventId) {
        logger.info("Get Event By id");
        return eventRepository.findById(eventId).orElse(null);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        logger.info("Get Events by title with page size " + pageSize + " and page number" + pageNum);
        Pageable page = PageRequest.of(pageNum - 1, pageSize);
        return eventRepository.findAllByTitle(title, page);
    }

    public Event create(Event event) {
        logger.info("Put Event. Event" + event);
        return eventRepository.save(event);
    }

    public Event update(Event event) {
        logger.info("Update Event. Event" + event);
        return eventRepository.save(event);
    }

    public void delete(long eventId) {
        logger.info("Delete Event from storage. Event id" + eventId);
        eventRepository.deleteById(eventId);
    }
}
