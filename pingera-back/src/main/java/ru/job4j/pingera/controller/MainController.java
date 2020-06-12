package ru.job4j.pingera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.job4j.pingera.models.Task;
import ru.job4j.pingera.repositories.TasksRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class MainController {

    @Autowired
    private TasksRepository t;

    @GetMapping(value = "/getall")
    public List<Task> GetAllTask() {
        List<Task> result = new ArrayList<>();
        t.findAll().forEach(x -> result.add(x));
        return result;
    }

    @GetMapping(value = "/get/{id}")
    public List<Task> GetAllTaskByUserId(@PathVariable int id) {
        List<Task> res = this.GetAllTask().stream().filter((x) -> x.getUser().getId() == id).collect(Collectors.toList());
        return res;
    }

    @PostMapping(value = "/post/{id}")
    public String PostTask(@PathVariable int id, @ModelAttribute Task newtask) {
        String res ="";
        if (newtask.getUser().getId() == id) {
            t.save(newtask);
            res = "Ok";
        }
        return res;
    }
}