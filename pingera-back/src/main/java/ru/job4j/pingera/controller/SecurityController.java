package ru.job4j.pingera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.job4j.pingera.models.User;
import ru.job4j.pingera.repositories.UsersRepository;

@RestController
@CrossOrigin("*")
public class SecurityController {
    @Autowired
    private UsersRepository usdb;

    @PostMapping("/getuser")
    public User getUser(@RequestBody User user) {
        User usertemp = usdb.findByPasswordAndName(user.getPassword(), user.getName());
        return usertemp;
    }
}
