package ru.job4j.pingera.controller;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.*;
        import ru.job4j.pingera.dto.TaskDto;
        import ru.job4j.pingera.dto.UserDto;
        import ru.job4j.pingera.models.Task;
        import ru.job4j.pingera.repositories.TasksRepository;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")

public class MainController {

    @Autowired
    private TasksRepository t;
    @Autowired
    private UserDto udto;

    @GetMapping(value = "/getalltasks")
    public List<Task> GetAllTask() {
        List<Task> result = new ArrayList<>();
        t.findAll().forEach(x -> result.add(x));
        return result;
    }

    @GetMapping(value = "/getalltasks/{id}")
    public List<Task> GetAllTaskByUserId(@PathVariable int id) {
        List<Task> res = this.GetAllTask().stream().filter((x) -> x.getUser().getId() == id).collect(Collectors.toList());
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

    @PostMapping(value = "/posttask")
    public void PostTask(@RequestBody TaskDto newtask) {
        if (newtask.getName1() != null) {
            UserDto nnn = udto;
            nnn.setName("Admin");
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
