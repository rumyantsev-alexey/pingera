package ru.job4j.pingera;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.pingera.clasez.SubTaskUtility;
import ru.job4j.pingera.models.SubTask;
import ru.job4j.pingera.models.Task;
import ru.job4j.pingera.models.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class useTest {


    @Autowired
    private SubTaskUtility stu;

    @Test
    public void test() {
        User us = new User();
        Task t = new Task();
        SubTask st = new SubTask();
        t.setUser(us);
        st.setTask(t);
        st.setResult("Non show".getBytes());
        System.out.println("---------------------------- Print user");
        System.out.println(us);
        System.out.println("---------------------------- Print task");
        System.out.println(t);
        System.out.println("---------------------------- Print subtask");
        System.out.println(st);
        System.out.println("---------------------------- End");
    }

    @Test
    public void  correctHost() throws IOException {
        SubTaskUtility stu = new SubTaskUtility();
        String host = "ya.ru";
        assertTrue(stu.isCorrectHost(host));
    }

}
