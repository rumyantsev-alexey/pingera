package ru.job4j.pingera.clasez;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.pingera.models.SubTask;

@Component
public class SelectToolHeandler {

    private static final Logger LOG = LogManager.getLogger(SelectToolHeandler.class.getName());

    @Autowired
    private SendTelegramm set;

    @Autowired
    private SendMail sm;

    public void getResultWithTools(SubTask st) {
        LOG.info(String.format("Send result of subtask № %s  task № %s in %s %s", st.getId(), st.getTask().getId(), st.getTask().getSellist4(), st.getTask().getText4()));
        switch (st.getTask().getSellist4()) {
            case email:
                sm.sendEmailResultCompleteSubTasksByTask(st);
                break;
            case telegramm:
                set.sendTelgeramMesAboutCompleteSubTask(st);
                break;
            default:
                break;
        }
    }

}
