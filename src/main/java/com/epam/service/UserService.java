package com.epam.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.epam.dao.UserRepository;
import com.epam.entity.User;
import com.epam.entity.UserAccount;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
    private static final Logger logger = LogManager.getLogger();
    private final UserRepository userRepository;

    public User getUserById(long userId) {
        logger.info("Get user by id:" + userId);
        return userRepository.findById(userId).orElse(null);
    }

    public User getUserByEmail(String email) {
        logger.info("Get user by email:" + email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        logger.info("Get user by name:" + name + ",page size:" + pageSize + ",page number:" + pageNum);
        Pageable page = PageRequest.of(pageNum - 1, pageSize);
        return userRepository.findAllByName(name, page);
    }

    public User create(User user) {
        logger.info("Input user in storage. User:" + user);
        user.setUserAccount(new UserAccount(BigDecimal.ZERO));
        return userRepository.save(user);
    }

    public User update(User user) {
        logger.info("Update user:" + user);
        return userRepository.save(user);
    }

    public void delete(long userId) {
        logger.info("Delete user by id:" + userId);
        userRepository.deleteById(userId);
    }
}
