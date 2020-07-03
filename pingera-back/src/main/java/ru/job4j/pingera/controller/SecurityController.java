package ru.job4j.pingera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.pingera.models.User;
import ru.job4j.pingera.repositories.UsersRepository;

@RestController
@CrossOrigin("*")
public class SecurityController {
    @Autowired
    private UsersRepository usdb;

    @PostMapping("/login")
    public boolean login(@RequestBody User user) {
        User usertemp = usdb.findByPasswordAndName(user.getPassword(), user.getName());
        return usertemp != null;
    }
}
