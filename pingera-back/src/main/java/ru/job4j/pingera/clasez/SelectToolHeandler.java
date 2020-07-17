package ru.job4j.pingera.clasez;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.pingera.models.SubTask;
import ru.job4j.pingera.telegramm.MelkonBot;

@Component
public class SelectToolHeandler {

    @Autowired
    private MelkonBot mb;

    @Autowired
    private SendMail sm;

    public void getResultWithTools(SubTask st) {
        switch (st.getTask().getSellist4()) {
            case email:
                sm.sendEmailResultCompleteSubTasksByTask(st);
                break;
            case telegramm:
                mb.send(st.getResult().toString());
                break;
            default:
                break;
        }
    }

}
