package ru.job4j.pingera.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class checkTestMailTest {

    @Autowired
    private JavaMailSender emailSender;

    @Test
    public void sendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("ralex5447@gmail.com");
        message.setTo("telesyn73@mail.ru");
        message.setSubject("test");
        message.setText("text");
        emailSender.send(message);
        assertTrue(true);
    }

}
