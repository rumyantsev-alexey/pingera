package ru.job4j.pingera.clasez;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.pingera.models.SubTask;
import ru.job4j.pingera.telegramm.MelkonBot;

@Component
public class SendTelegramm {

    @Autowired
    private MelkonBot mb;

    public void sendTelgeramMesAboutCompleteSubTask(SubTask st) {
        mb.setChatId(Long.valueOf(st.getTask().getText4()));
        StringBuilder text = new StringBuilder();
        text.append(String.format("Result subtask №%s of task №%s", st.getId(), st.getTask().getId()));
        text.append(System.lineSeparator());
        text.append(st.getTask().toString());
        text.append(System.lineSeparator());
        text.append(st.toString());
        text.append(System.lineSeparator());
        mb.send(text.toString());
    }
}
