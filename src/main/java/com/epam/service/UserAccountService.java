package com.epam.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dao.UserAccountRepository;
import com.epam.entity.UserAccount;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserAccountService {

    private UserAccountRepository userAccountRepository;

    public void topUpUserAccount(long id, BigDecimal money) {
        userAccountRepository.topUpUserAccount(id, money);
    }
}
