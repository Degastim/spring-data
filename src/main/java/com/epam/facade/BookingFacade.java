package com.epam.facade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epam.entity.Event;
import com.epam.entity.Ticket;
import com.epam.entity.TicketContainer;
import com.epam.entity.User;
import com.epam.service.EventService;
import com.epam.service.TicketService;
import com.epam.service.UserAccountService;
import com.epam.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Setter
@Component
public class BookingFacade {
    private final EventService eventService;
    private final TicketService ticketService;
    private final UserService userService;
    private final UserAccountService userAccountService;
    private final XmlConverter xmlConverter;

    public Event getEventById(long eventId) {
        return eventService.getEventById(eventId);
    }

    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    public Event createEvent(Event event) {
        return eventService.create(event);
    }

    public Event updateEvent(Event event) {
        return eventService.update(event);
    }

    public void deleteEvent(long eventId) {
        eventService.delete(eventId);
    }

    public User getUserById(long userId) {
        return userService.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        return userService.getUserByEmail(email);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return userService.getUsersByName(name, pageSize, pageNum);
    }

    public User createUser(User user) {
        return userService.create(user);
    }

    public User updateUser(User user) {
        return userService.update(user);
    }

    public void deleteUser(long userId) {
        userService.delete(userId);
    }

    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category, BigDecimal cost) {
        Ticket ticket = new Ticket(new User(userId), new Event(eventId), place, category, cost);
        return ticketService.bookTicket(ticket);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    public void cancelTicket(long ticketId) {
        ticketService.delete(ticketId);
    }

    @Transactional
    public void preloadTickets(String xmlFile) throws IOException {
        TicketContainer ticketContainer = xmlConverter.convertFromXmlToObject(xmlFile);
        ticketContainer.getTicketList().forEach(ticketService::bookTicket);
    }

    public void topUpUserAccount(Long id, BigDecimal money) {
        userAccountService.topUpUserAccount(id, money);
    }
}
