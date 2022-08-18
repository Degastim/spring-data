package com.epam.dao;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.epam.entity.UserAccount;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
    @Modifying(flushAutomatically = true)
    @Query("UPDATE UserAccount UA SET UA.money=UA.money+:sum WHERE UA.id=:id")
    void topUpUserAccount(long id, BigDecimal sum);
}