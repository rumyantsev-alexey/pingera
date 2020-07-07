package ru.job4j.pingera.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.pingera.models.Task;
import ru.job4j.pingera.models.User;

import java.util.List;

@Repository
public interface TasksRepository extends CrudRepository<Task, Long> {
    List<Task> findAllByActual(boolean actual);
    List<Task> findAllByUserAndActual(User user, boolean actual);
    List<Task> findAllByActualAndReport(boolean actual, boolean report);
}
