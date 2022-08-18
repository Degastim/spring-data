package com.epam.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.entity.User;
import com.epam.facade.BookingFacade;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private BookingFacade facade;

    @GetMapping("/find")
    public String getUserById(@RequestParam long id, Model model) {
        logger.info("Handle a request to find a user by id");
        User user = facade.getUserById(id);
        if (user == null) {
            model.addAttribute("result", "Nothing");
        } else {
            model.addAttribute("users", new User[] {user});
        }
        return "users/user";
    }

    @GetMapping("/findByEmail")
    public String getUserByEmail(@RequestParam String email, Model model) {
        logger.info("Handle a request to find a user by email");
        User user = facade.getUserByEmail(email);
        if (user == null) {
            model.addAttribute("result", "Nothing");
        } else {
            model.addAttribute("users", new User[] {user});
        }
        return "users/user";
    }

    @GetMapping("/findByUsername")
    public String getUsersByName(@RequestParam String name, @RequestParam int pageSize, @RequestParam int pageNum,
                                 Model model) {
        logger.info("Handle a request to find a user by name");
        List<User> userList = facade.getUsersByName(name, pageSize, pageNum);
        if (userList.size() == 0) {
            model.addAttribute("result", "Nothing");
        } else {
            model.addAttribute("users", userList);
        }
        return "users/user";
    }

    @PostMapping
    public String createUser(@RequestParam String name, @RequestParam String email, Model model) {
        logger.info("Handle a request to create a user");
        User user = new User(name, email);
        user = facade.createUser(user);
        model.addAttribute("users", new User[] {user});
        return "users/user";
    }

    @PutMapping
    public String updateUser(@RequestParam long id, @RequestParam String name, @RequestParam String email,
                             Model model) {
        logger.info("Handle a request to update a user");
        User user = new User(id, name, email);
        user = facade.updateUser(user);
        model.addAttribute("users", new User[] {user});
        return "users/user";
    }

    @DeleteMapping
    public String deleteUser(@RequestParam long id, Model model) {
        logger.info("Handle a request to delete a user");
        facade.deleteUser(id);
        model.addAttribute("result", true);
        return "users/user";
    }
}
