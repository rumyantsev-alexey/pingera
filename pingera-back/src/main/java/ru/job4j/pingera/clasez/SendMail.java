package ru.job4j.pingera.clasez;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.job4j.pingera.models.SubTask;

@Component
public class SendMail {


    @Autowired
    private JavaMailSender mail;

    public void sendEmailResultCompleteSubTasksByTask(SubTask st) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(st.getTask().getText4());
            message.setSubject(String.format("Result subtask №%s of task №%s", st.getId(), st.getTask().getId()));
            String res = st.getTask().toString() + System.lineSeparator();
            res += st.toString() + System.lineSeparator();
            message.setText(res);
            mail.send(message);
    }


}
