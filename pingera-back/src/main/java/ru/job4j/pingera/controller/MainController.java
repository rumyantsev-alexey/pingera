package ru.job4j.pingera.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.job4j.pingera.clasez.ConvertTaskToSubtasks;
import ru.job4j.pingera.dto.TaskDto;
import ru.job4j.pingera.dto.UserDto;
import ru.job4j.pingera.models.Task;
import ru.job4j.pingera.models.User;
import ru.job4j.pingera.repositories.SubTaskRepository;
import ru.job4j.pingera.repositories.TasksRepository;
import ru.job4j.pingera.repositories.UsersRepository;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")

public class MainController {

    @Autowired
    private TasksRepository t;
    @Autowired
    private UserDto udto;
    @Autowired
    private UsersRepository u;
    @Autowired
    private SubTaskRepository st;

    @GetMapping(value = "/getalltasksforauthuser")
    public List<Task> GetAllTask(Principal principal) {
        List<Task> result = new ArrayList<>();
        User user = u.findByName(principal.getName());
        t.findAllByActualAndSplit(true, false).forEach(x -> {
            if (x.getUser().getId() == user.getId()) {
                result.add(x);
            }
        });
         return result;
    }

    @PostMapping(value = "/posttask")
    public void PostTask(@RequestBody TaskDto newtask, Principal principal) {
        if (newtask.getName1() != null && principal != null) {
            UserDto nnn = udto;
            nnn.setName(principal.getName());
            Task ntask = newtask.convertToTask(nnn);
            ntask.setSplit(true);
            ntask.setActual(true);
            ntask = t.save(ntask);
            st.saveAll(new ConvertTaskToSubtasks().convert(ntask));
        }
    }

    @DeleteMapping(value = "/deletetask/{id}")
    public void deleteTaskByTaskId(@PathVariable long id) {
        if (t.findById(id).isPresent()) {
            t.deleteById(id);
        }
    }

    @PostMapping(value = "/adduser")
    public void addUser(@RequestBody User newuser) {
    u.save(newuser);
    }

}
