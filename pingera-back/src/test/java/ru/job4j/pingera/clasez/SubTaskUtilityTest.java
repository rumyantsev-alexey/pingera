package ru.job4j.pingera.clasez;

import org.junit.jupiter.api.Test;
import ru.job4j.pingera.models.SubTask;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskUtilityTest {
    SubTaskUtility stu = new SubTaskUtility();

    @Test
    void checkActualTasks() {
        assertTrue(stu.isCorrectHost("ya.ru"));
    }
}
