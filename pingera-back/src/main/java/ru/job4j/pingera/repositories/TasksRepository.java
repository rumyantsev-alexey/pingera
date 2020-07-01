package ru.job4j.pingera.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.pingera.models.Task;

import java.util.List;

@Repository
public interface TasksRepository extends CrudRepository<Task, Long> {
    List<Task> findAllByActual(boolean actual);
    List<Task> findAllByActualAndSplit(boolean actual, boolean split);
}
