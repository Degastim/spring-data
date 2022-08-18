package com.epam.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.epam.dao.TicketRepository;
import com.epam.dao.UserRepository;
import com.epam.entity.Event;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.entity.UserAccount;

class TicketServiceTest {
    private TicketService ticketService;
    private TicketRepository ticketRepository;
    private UserRepository userRepository;

    @BeforeEach
    void ticketService() {
        ticketRepository = Mockito.mock(TicketRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        ticketService = new TicketService(ticketRepository, userRepository);
    }

    @Test
    void getBookedTickets() {
        //Given
        User user = new User(1, "Dave", "tandra@gmail.com");
        Ticket ticket1 = new Ticket(1, new User(1), new Event(2), 3, Ticket.Category.BAR, BigDecimal.ZERO);
        Ticket ticket2 = new Ticket(2, new User(1), new Event(2), 4, Ticket.Category.BAR, BigDecimal.ZERO);
        List<Ticket> ticketList = List.of(ticket1, ticket2);
        Mockito.when(ticketRepository.findAllByUser(user, PageRequest.of(0, 2, Sort.by("date").ascending())))
                .thenReturn(ticketList);
        List<Ticket> expected = List.of(ticket1, ticket2);

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
        Mockito.when(ticketRepository.findAllByEvent(event, PageRequest.of(0, 2, Sort.by("email").descending())))
                .thenReturn(ticketList);
        List<Ticket> expected = List.of(ticket1, ticket2);

        //When
        List<Ticket> actual = ticketService.getBookedTickets(event, 2, 1);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void bookTicket_placedBook_exception() {
        //Given
        Ticket ticket = new Ticket(new User(1), new Event(2), 3, Ticket.Category.BAR, BigDecimal.ZERO);
        Mockito.when(ticketRepository.existsByEventAndPlace(new Event(2), 3)).thenReturn(true);

        //When-Then
        Assertions.assertThrows(IllegalStateException.class, () -> ticketService.bookTicket(ticket));
    }

    @Test
    void bookTicket_userNotFound_ticket() {
        //Given
        Ticket ticket = new Ticket(new User(1), new Event(1), 2, Ticket.Category.BAR, BigDecimal.ZERO);
        Ticket savedTicket = new Ticket(1, new User(1), new Event(1), 2, Ticket.Category.BAR, BigDecimal.ZERO);
        Mockito.when(ticketRepository.existsByEventAndPlace(new Event(1), 2)).thenReturn(false);
        Mockito.when(ticketRepository.save(ticket)).thenReturn(savedTicket);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //When-Then
        Assertions.assertThrows(IllegalArgumentException.class, () -> ticketService.bookTicket(ticket));
    }

    @Test
    void bookTicket_userDoesntHavaEnoughMoney_ticket() {
        //Given
        Ticket ticket = new Ticket(new User(1), new Event(1), 2, Ticket.Category.BAR, BigDecimal.TEN);
        Ticket savedTicket = new Ticket(1, new User(1), new Event(1), 2, Ticket.Category.BAR, BigDecimal.ZERO);
        Mockito.when(ticketRepository.existsByEventAndPlace(new Event(1), 2)).thenReturn(false);
        Mockito.when(ticketRepository.save(ticket)).thenReturn(savedTicket);
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(new User(1, "Dan", "dan@gmail.com", new UserAccount(1, BigDecimal.ZERO))));
        Ticket expected = new Ticket(1, new User(1), new Event(1), 2, Ticket.Category.BAR, BigDecimal.ZERO);

        //When-Then
        Assertions.assertThrows(IllegalStateException.class, () -> ticketService.bookTicket(ticket));
    }

    @Test
    void bookTicket_notPlacedBook_ticket() {
        //Given
        Ticket ticket = new Ticket(new User(1), new Event(1), 2, Ticket.Category.BAR, BigDecimal.ZERO);
        Ticket savedTicket = new Ticket(1, new User(1), new Event(1), 2, Ticket.Category.BAR, BigDecimal.ZERO);
        Mockito.when(ticketRepository.existsByEventAndPlace(new Event(1), 2)).thenReturn(false);
        Mockito.when(ticketRepository.save(ticket)).thenReturn(savedTicket);
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(new User(1, "Dan", "dan@gmail.com", new UserAccount(1, BigDecimal.ZERO))));
        Ticket expected = new Ticket(1, new User(1), new Event(1), 2, Ticket.Category.BAR, BigDecimal.ZERO);

        //When
        Ticket actual = ticketService.bookTicket(ticket);

        //Then
        Assertions.assertEquals(expected, actual);
    }
}