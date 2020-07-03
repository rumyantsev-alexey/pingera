package ru.job4j.pingera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.job4j.pingera.clasez.SubTaskUtility;
import ru.job4j.pingera.models.SubTask;
import ru.job4j.pingera.models.Task;
import ru.job4j.pingera.models.User;
import ru.job4j.pingera.repositories.SubTaskRepository;
import ru.job4j.pingera.repositories.TasksRepository;
import ru.job4j.pingera.repositories.UsersRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")

public class MainController {

    @Autowired
    private TasksRepository t;
    @Autowired
    private UsersRepository u;
    @Autowired
    private SubTaskRepository st;

    @GetMapping(value = "/getalltasksforauthuser")
    public List<Task> GetAllActualTask(Principal principal) {
        List<Task> result = new ArrayList<>();
        User user = u.findByName(principal.getName());
        result = t.findAllByUserAndActual(user, true);
        return result;
    }

    @Transactional
    @PostMapping(value = "/posttask")
    public void PostTask(@RequestBody Task newtask, Principal principal) {
        if (newtask.getName1() != null && principal != null) {
            newtask.setSplit(true);
            newtask.setActual(true);
            newtask = t.save(newtask);
            st.saveAll(new SubTaskUtility().convert(newtask));
        }
    }

    @Transactional
    @DeleteMapping(value = "/deletetask/{id}")
    public void deleteTaskByTaskId(@PathVariable long id) {
        if (t.findById(id).isPresent()) {
            Task task = t.findById(id).get();
            st.deleteAllByTask(task);
            t.deleteById(id);
        }
    }

    @GetMapping(value = "/getallcompletetasksforauthuser")
    public List<Task> GetAllCompleteTask(Principal principal) {
        User user = u.findByName(principal.getName());
        List<Task> result = t.findAllByUserAndActual(user, false);
        return result;
    }


    @Transactional
    @PostMapping(value = "/adduser")
        public void addUser(@RequestBody User newuser) {
        u.save(newuser);
    }

    @GetMapping(value = "/getallcompletesubtasksfortask/{id}")
    public List<SubTask> GetAllCompleteSubTaskByTaskId(@PathVariable long id) {
        Task task = t.findById(id).get();
        List<SubTask> result = st.findAllByTaskAndComplete(task, true);
        return result;
    }

}
