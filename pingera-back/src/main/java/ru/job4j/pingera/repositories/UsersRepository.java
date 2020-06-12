package ru.job4j.pingera.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.pingera.models.User;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {
    User findByName(String name);
}
