package ru.job4j.pingera.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.pingera.models.SubTask;

import java.util.List;

@Repository
public interface SubTaskRepository extends CrudRepository<SubTask, Long> {
//    Iterable findAllByAt_workAndComplete(boolean at_work, boolean complete);
}
