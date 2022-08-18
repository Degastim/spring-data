package com.epam.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_accounts")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_account_id")
    private long id;
    private BigDecimal money;
    @OneToOne(mappedBy = "userAccount")
    private User user;

    public UserAccount(long id, BigDecimal money) {
        this.id = id;
        this.money = money;
    }

    public UserAccount(BigDecimal money) {
        this.money = money;
    }
}
