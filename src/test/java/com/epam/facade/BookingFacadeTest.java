package com.epam.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.epam.entity.Event;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.service.EventService;
import com.epam.service.TicketService;
import com.epam.service.UserAccountService;
import com.epam.service.UserService;

class BookingFacadeTest {
    private EventService eventService;
    private TicketService ticketService;
    private UserService userService;
    private BookingFacade facade;
    private XmlConverter converter;
    private UserAccountService userAccountService;

    @BeforeEach
    void setup() {
        eventService = Mockito.mock(EventService.class);
        ticketService = Mockito.mock(TicketService.class);
        userService = Mockito.mock(UserService.class);
        converter = Mockito.mock(XmlConverter.class);
        userAccountService = Mockito.mock(UserAccountService.class);
        facade = new BookingFacade(eventService, ticketService, userService, userAccountService, converter);
    }

    @Test
    void getEventById() {
        //Given
        long eventId = 3;
        Event event1 = new Event(eventId, "Music", new GregorianCalendar(2022, Calendar.APRIL, 4).getTime());
        Event expected = new Event(eventId, "Music", new GregorianCalendar(2022, Calendar.APRIL, 4).getTime());
        Mockito.when(eventService.getEventById(eventId)).thenReturn(event1);

        //When
        Event actual = facade.getEventById(eventId);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getEventsByTitle() {
        //Given
        String title = "Music";
        Event event1 = new Event(3L, title, new GregorianCalendar(2022, Calendar.APRIL, 4).getTime());
        Event event2 = new Event(5L, title, new GregorianCalendar(2022, Calendar.AUGUST, 3).getTime());
        List<Event> eventList = List.of(event1, event2);
        List<Event> expected = new ArrayList<>();
        expected.add(event1);
        expected.add(event2);
        Mockito.when(eventService.getEventsByTitle(title, 2, 1)).thenReturn(eventList);

        //When
        List<Event> actual = facade.getEventsByTitle(title, 2, 1);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createEvent() {
        //Given
        Event event = new Event(1, "Dance", new GregorianCalendar(2022, Calendar.APRIL, 4).getTime());
        Event expected = new Event(1, "Dance", new GregorianCalendar(2022, Calendar.APRIL, 4).getTime());
        Mockito.when(eventService.create(event)).thenReturn(event);

        //When
        Event actual = facade.createEvent(event);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void updateEvent() {
        //Given
        Event updateEvent = new Event(1, "Music", new GregorianCalendar(2023, Calendar.APRIL, 4).getTime());
        Event expected = new Event(1, "Music", new GregorianCalendar(2023, Calendar.APRIL, 4).getTime());
        Mockito.when(eventService.update(updateEvent)).thenReturn(updateEvent);

        //When
        Event actual = facade.updateEvent(updateEvent);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getUserById() {
        //Given
        long userId = 3;
        User user1 = new User(userId, "Dave", "dave@gmail.com");
        User expected = new User(userId, "Dave", "dave@gmail.com");
        Mockito.when(userService.getUserById(userId)).thenReturn(user1);

        //When
        User actual = facade.getUserById(userId);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getUserByEmail() {
        //Given
        User user = new User(3, "Dave", "dave@gmail.com");
        User expected = new User(3L, "Dave", "dave@gmail.com");
        Mockito.when(userService.getUserByEmail("dave@gmail.com")).thenReturn(user);

        //When
        User actual = facade.getUserByEmail("dave@gmail.com");

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getUsersByName() {
        //Given
        User user1 = new User(3, "Dave", "dave1@gmail.com");
        User user2 = new User(5, "Dave", "dave2@gmail.com");
        List<User> userList = List.of(user1, user2);
        Mockito.when(userService.getUsersByName("Dave", 2, 2)).thenReturn(userList);
        List<User> expected = List.of(user1, user2);

        //When
        List<User> actual = facade.getUsersByName("Dave", 2, 2);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create_correctUser_equals() {
        //Given
        User user = new User(1, "Dave", "dave2@gmail.com");
        Mockito.when(userService.create(user)).thenReturn(user);
        User expected = new User(1, "Dave", "dave2@gmail.com");

        //When
        User actual = facade.createUser(user);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create_incorrectUser_exception() {
        //Given
        User user = new User(2, "Dave", "dave1@gmail.com");
        Mockito.when(userService.create(user)).thenThrow(new IllegalStateException());

        //When-Then
        Assertions.assertThrows(IllegalStateException.class, () -> userService.create(user));
    }

    @Test
    void updateUser() {
        //Given
        User user = new User(1, "Jacob", "dave1@gmail.com");
        User expected = new User(1, "Jacob", "dave1@gmail.com");
        Mockito.when(userService.update(user)).thenReturn(user);

        //When
        User actual = facade.updateUser(user);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteUserFalse() {
        //Given
        Mockito.doThrow(IllegalArgumentException.class).when(userService).delete(1L);
        //Then-When
        Assertions.assertThrows(
                IllegalArgumentException.class, () -> facade.deleteUser(1L));
    }

    @Test
    void bookTicket_placedBook_exception() {
        //Given
        Ticket ticket = new Ticket(new User(1), new Event(2), 3, Ticket.Category.BAR, BigDecimal.ZERO);
        Mockito.when(ticketService.bookTicket(ticket)).thenThrow(IllegalStateException.class);

        //When-Then
        Assertions.assertThrows(IllegalStateException.class,
                () -> facade.bookTicket(1, 2, 3, Ticket.Category.BAR, BigDecimal.ZERO));
    }

    @Test
    void bookTicket_notPlacedBook_ticket() {
        //Given
        Ticket ticket = new Ticket(new User(1), new Event(2), 3, Ticket.Category.BAR, BigDecimal.ZERO);
        Ticket bookedTicket = new Ticket(1, new User(1), new Event(2), 3, Ticket.Category.BAR, BigDecimal.ZERO);
        Mockito.when(ticketService.bookTicket(ticket)).thenReturn(bookedTicket);
        Ticket expected = new Ticket(1, new User(1), new Event(2), 3, Ticket.Category.BAR, BigDecimal.ZERO);

        //When
        Ticket actual = ticketService.bookTicket(ticket);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getBookedTickets() {
        //Given
        User user = new User(1, "Dave", "tandra@gmail.com");
        Ticket ticket1 = new Ticket(1, new User(1), new Event(2), 3, Ticket.Category.BAR, BigDecimal.ZERO);
        Ticket ticket2 = new Ticket(2, new User(1), new Event(2), 4, Ticket.Category.BAR, BigDecimal.ZERO);
        List<Ticket> ticketList = List.of(ticket1, ticket2);
        List<Ticket> expected = List.of(ticket1, ticket2);
        Mockito.when(ticketService.getBookedTickets(user, 2, 1)).thenReturn(ticketList);

        //When
        List<Ticket> actual = ticketService.getBookedTickets(user, 2, 1);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetBookedTickets() {
        //Given
        Event event = new Event(2, "Dance", new GregorianCalendar(2022, Calendar.APRIL, 4).getTime());
        Ticket ticket1 = new Ticket(1, new User(1), new Event(2), 3, Ticket.Category.BAR, BigDecimal.ZERO);
        Ticket ticket2 = new Ticket(2, new User(2), new Event(2), 4, Ticket.Category.BAR, BigDecimal.ZERO);
        List<Ticket> ticketList = List.of(ticket1, ticket2);
        List<Ticket> expected = List.of(ticket1, ticket2);
        Mockito.when(ticketService.getBookedTickets(event, 2, 1)).thenReturn(ticketList);

        //When
        List<Ticket> actual = ticketService.getBookedTickets(event, 2, 1);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testTopUpUserAccount() {
        facade.topUpUserAccount(1L, BigDecimal.TEN);
        Mockito.verify(userAccountService).topUpUserAccount(1, BigDecimal.TEN);
    }
}