package com.epam.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;

import com.epam.dao.UserRepository;
import com.epam.entity.User;

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void getUserById() {
        //Given
        long userId = 3;
        User user = new User(userId, "Dave", "dave@gmail.com");
        User expected = new User(userId, "Dave", "dave@gmail.com");
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //When
        User actual = userService.getUserById(userId);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getUserByEmail() {
        //Given
        User user = new User(3, "Dave", "dave@gmail.com");
        User expected = new User(3L, "Dave", "dave@gmail.com");
        Mockito.when(userRepository.findByEmail("dave@gmail.com")).thenReturn(Optional.of(user));

        //When
        User actual = userService.getUserByEmail("dave@gmail.com");

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getUsersByName() {
        //Given
        User user1 = new User(3, "Dave", "dave1@gmail.com");
        User user2 = new User(5, "Dave", "dave2@gmail.com");
        List<User> userList = List.of(user1, user2);
        Mockito.when(userRepository.findAllByName("Dave", PageRequest.of(1, 2))).thenReturn(userList);
        List<User> expected = List.of(user1, user2);

        //When
        List<User> actual = userService.getUsersByName("Dave", 2, 2);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create_correctUser_equals() {
        //Given
        User user = new User(1, "Dave", "dave2@gmail.com");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User expected = new User(1, "Dave", "dave2@gmail.com");

        //When
        User actual = userService.create(user);

        //Then
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void create_incorrectUser_exception() {
        //Given
        User user = new User(2, "Dave", "dave1@gmail.com");
        Mockito.when(userRepository.save(user)).thenThrow(new IllegalStateException());

        //When-Then
        Assertions.assertThrows(IllegalStateException.class, () -> userRepository.save(user));
    }

    @Test
    void update() {
        //Given
        User user = new User(1, "Jacob", "dave1@gmail.com");
        User expected = new User(1, "Jacob", "dave1@gmail.com");
        Mockito.when(userRepository.save(user)).thenReturn(user);

        //When
        User actual = userService.update(user);

        //Then
        Assertions.assertEquals(expected, actual);
    }
}