package com.epam.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.epam.entity.Event;
import com.epam.entity.Ticket;
import com.epam.entity.User;

@Repository
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {
    List<Ticket> findAllByUser(User user, Pageable pageable);

    List<Ticket> findAllByEvent(Event event, Pageable pageable);

    boolean existsByEventAndPlace(Event event, int place);
}
