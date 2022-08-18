package com.epam.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlType(propOrder = {"user", "event", "place", "category","ticketPrice"})
@Entity
@Table(name = "tickets")
public class Ticket {
    public Ticket(User user, Event event, int place, Category category) {
        this.user = user;
        this.event = event;
        this.place = place;
        this.category = category;
    }

    public Ticket(User user, Event event, int place, Category category, BigDecimal ticketPrice) {
        this.user = user;
        this.event = event;
        this.place = place;
        this.category = category;
        this.ticketPrice = ticketPrice;
    }

    public enum Category {
        STANDARD,
        PREMIUM,
        BAR
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id")
    private Event event;
    private int place;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Column(name = "ticket_price")
    private BigDecimal ticketPrice;

    @XmlAttribute(name = "id")
    public long getId() {
        return id;
    }
}
