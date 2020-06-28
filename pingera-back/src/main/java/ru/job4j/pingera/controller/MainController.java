package ru.job4j.pingera.controller;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;
        import ru.job4j.pingera.dto.TaskDto;
        import ru.job4j.pingera.dto.UserDto;
        import ru.job4j.pingera.models.Task;
        import ru.job4j.pingera.models.User;
        import ru.job4j.pingera.repositories.TasksRepository;
        import ru.job4j.pingera.repositories.UsersRepository;

        import java.security.Principal;
        import java.util.ArrayList;
        import java.util.LinkedList;
        import java.util.List;
        import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")

public class MainController {

    @Autowired
    private TasksRepository t;
    @Autowired
    private UserDto udto;
    @Autowired
    private UsersRepository u;

    @GetMapping(value = "/getalltasksforauthuser")
    public List<Task> GetAllTask(Principal principal) {
        List<Task> result = new ArrayList<>();
        User user = u.findByName(principal.getName());
        t.findAll().forEach(x -> {
            if (x.getUser().getId() == user.getId()) {
                result.add(x);
            }
        });
         return result;
    }

/*    @GetMapping(value = "/getalltasks/{id}")
    public List<Task> GetAllTaskByUserId(@PathVariable int id) {
        List<Task> res = new LinkedList<>();
        return res;
    }
    @GetMapping(value = "/gettask/{id}")
    public Task GetAllTaskByTaskId(@PathVariable long id) {
        Task res = t.findById(id).orElse(new Task());
        return res;
    }

    @PostMapping(value = "/posttask/{id}")
    public void PostTask(@PathVariable int id, @ModelAttribute Task newtask) {
        if (newtask.getUser().getId() == id) {
            t.save(newtask);
        }
    }
*/
    @PostMapping(value = "/posttask")
    public void PostTask(@RequestBody TaskDto newtask, Principal principal) {
        if (newtask.getName1() != null) {
            UserDto nnn = udto;
            nnn.setName(principal.getName());
            Task ntask = newtask.convertToTask(nnn);
            t.save(ntask);
        }
    }

    @DeleteMapping(value = "/deletetask/{id}")
    public void deleteTaskByTaskId(@PathVariable long id) {
        if (t.findById(id).isPresent()) {
            t.deleteById(id);
        }
    }
}
