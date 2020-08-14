package ru.job4j.pingera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.pingera.models.User;
import ru.job4j.pingera.repositories.UsersRepository;

@RestController
@CrossOrigin("*")
public class SecurityController {
    @Autowired
    private UsersRepository usdb;

    @Autowired
    private PasswordEncoder pe;

    @PostMapping("/login")
    public User isValidUser(@RequestBody User usr) {
        User user = usdb.findByName(usr.getName());
        return (user != null && pe.matches(usr.getPassword(), user.getPassword())) ? user: null;
    }
}
