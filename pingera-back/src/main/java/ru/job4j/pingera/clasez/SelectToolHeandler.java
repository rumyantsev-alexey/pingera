package ru.job4j.pingera.clasez;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.pingera.models.SubTask;

@Component
public class SelectToolHeandler {

    @Autowired
    private SendMail sm;

    public ResultOfNetworkTools getResultWithTools(SubTask st) {
        ResultOfNetworkTools result = new ResultPingTypeImplIcmp4J();
        switch (st.getTask().getSellist4()) {
            case email:
                sm.sendEmailResultCompleteSubTasksByTask(st);
                break;
            case telegramm:
                //todo сделать telegramm
                result.setResult("Telegramm not working yet");
                break;
            default:
                result.setResult("ToolHeandler not found");
                break;
        }
        return result;
    }
}
