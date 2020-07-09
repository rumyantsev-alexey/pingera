package ru.job4j.pingera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity PostTask(@RequestBody Task newtask, Principal principal) {
        if (newtask.getName1() != null && principal != null) {
            newtask.setSplit(true);
            newtask.setActual(true);
            User user = u.findByName(principal.getName());
            newtask.setUser(user);
            newtask.setActual(true);
            newtask.setSplit(true);
            newtask = t.save(newtask);
            st.saveAll(new SubTaskUtility().convert(newtask));
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    @DeleteMapping(value = "/deletetask/{id}")
    public ResponseEntity deleteTaskByTaskId(@PathVariable long id) {
        if (t.findById(id).isPresent()) {
            Task task = t.findById(id).get();
            st.deleteAllByTask(task);
            t.deleteById(id);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
        public ResponseEntity addUser(@RequestBody User newuser) {
        User usr = u.findByName(newuser.getName());
        if (usr == null) {
            u.save(newuser);
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping(value = "/getallcompletesubtasksfortask/{id}")
    public List<SubTask> GetAllCompleteSubTaskByTaskId(@PathVariable long id) {
        Task task = t.findById(id).get();
        List<SubTask> result = st.findAllByTaskAndComplete(task, true);
        return result;
    }

}
