package ru.job4j.pingera.telegramm;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MelkonBotTest {

    @Autowired
    private MelkonBot mb;

    @Test
    public void testTelegramBot() {
        mb.send("wqqq");
        assertTrue(true);
    }

}
