package com.epam.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.epam.entity.Event;

@Repository
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {
    List<Event> findAllByTitle(String title, Pageable pageable);
}
