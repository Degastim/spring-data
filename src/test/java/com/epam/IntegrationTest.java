package com.epam;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.epam.entity.Event;
import com.epam.entity.Ticket;
import com.epam.entity.User;
import com.epam.entity.UserAccount;
import com.epam.facade.BookingFacade;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = "/applicationContextMVC.xml")
public class IntegrationTest {
    @Autowired
    BookingFacade bookingFacade;

    @Test
    void test_correctValue_equals() {
        User user = new User("Jonson", "jonson@gmail.com", new UserAccount(BigDecimal.TEN));
        Event event = new Event("Dance", new GregorianCalendar(2020, Calendar.MAY, 6).getTime());
        user = bookingFacade.createUser(user);
        event = bookingFacade.createEvent(event);
        Ticket actual =
                bookingFacade.bookTicket(user.getId(), event.getId(), 3, Ticket.Category.BAR, BigDecimal.valueOf(3));
        Ticket expected = new Ticket(1, new User(1), new Event(1), 3, Ticket.Category.BAR, BigDecimal.valueOf(3));
        Assertions.assertEquals(expected, actual);
    }
}
