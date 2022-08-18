package com.epam.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.dao.TicketRepository;
import com.epam.dao.UserRepository;
import com.epam.entity.Event;
import com.epam.entity.Ticket;
import com.epam.entity.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TicketService {
    private static final Logger logger = LogManager.getLogger();
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Transactional
    public Ticket bookTicket(Ticket ticket) {
        Event event = ticket.getEvent();
        int place = ticket.getPlace();
        boolean isPlaceBooked = ticketRepository.existsByEventAndPlace(event, place);
        if (isPlaceBooked) {
            logger.warn("This place has already been booked.Place" + place + "for event" + event);
            throw new IllegalStateException("This place has already been booked.Place" + place + "for event" + event);
        }
        long userId = ticket.getUser().getId();
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("User ID not found"));
        if (user.getUserAccount() == null) {
            throw new IllegalArgumentException("User Account not found");
        }
        BigDecimal userMoney = user.getUserAccount().getMoney();
        BigDecimal ticketPrice = ticket.getTicketPrice();
        if (userMoney.compareTo(ticketPrice) >= 0) {
            user.getUserAccount().setMoney(userMoney.subtract(ticketPrice));
            userRepository.save(user);
        } else {
            throw new IllegalStateException("User doesn't have enough money");
        }
        logger.info("Input ticket.Ticket" + ticket);
        return ticketRepository.save(ticket);
    }

    public void delete(long ticketId) {
        logger.info("Delete ticket.Ticket id" + ticketId);
        ticketRepository.deleteById(ticketId);
    }

    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        logger.info("Get all booked tickets for specified user.Reverse by event date. User " + user + " page size:"
                + pageSize + "and Page number" + pageNum);
        Pageable page = PageRequest.of(pageNum - 1, pageSize, Sort.by("event.date").ascending());
        return ticketRepository.findAllByUser(user, page);
    }

    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        logger.info("Get all booked tickets for specified event. Event " + event + " page size" + pageSize
                + "and Page number" + pageNum);
        Pageable page = PageRequest.of(pageNum - 1, pageSize, Sort.by("user.email").descending());
        return ticketRepository.findAllByEvent(event, page);
    }
    /*
    .stream().sorted((o1, o2) -> o2.getEvent().getDate().compareTo(o1.getEvent().getDate()))
    .collect(Collectors.toList())
    .stream().sorted(Comparator.comparing(o -> o.getUser().getEmail())).collect(Collectors.toList());
     */
}
