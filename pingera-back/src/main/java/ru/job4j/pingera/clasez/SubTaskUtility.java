package ru.job4j.pingera.clasez;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.pingera.models.SubTask;
import ru.job4j.pingera.models.Task;
import ru.job4j.pingera.repositories.SubTaskRepository;
import ru.job4j.pingera.repositories.TasksRepository;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class SubTaskUtility {

    @Autowired
    private SubTaskRepository st;

    @Autowired
    private TasksRepository t;

    @Autowired
    private JavaMailSender mail;

    ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();

    public List<SubTask> convert(Task task) {
        List<SubTask> result = new ArrayList<>();
        long period;
        switch (task.getSellist2()) {
            case ("mins"):
                period = task.getText3() * 60 * 1000;
                break;
            case ("hrs"):
                period = task.getText3() * 60 * 60 * 1000;
                break;
            case ("days"):
                period = task.getText3() * 24 * 60 * 60 * 1000;
                break;
            case ("wks"):
                period = task.getText3() * 7 * 24 * 60 * 60 * 1000;
                break;
            case ("mns"):
                period = task.getText3() * 30* 7 * 24 * 60 * 60 * 1000;
                break;
            case ("yrs"):
                period = task.getText3() * 365* 24 * 60 * 60 * 1000;
                break;
            default:
                period = 0;
        }
        for(int i =1 ; i <= task.getTotal(); i++ ) {
            SubTask st = new SubTask();
            st.setDate1(new Timestamp(task.getDate1().getTime() + period * (i - 1)));
            st.setTask(task);
            st.setWork(false);
            st.setComplete(false);
            result.add(st);
        }
        return result;
    }

     public boolean isCorrectHost(String name) {
        boolean result = true;
        InetAddress inet;
        try {
            inet = InetAddress.getByName(name);
            if (!inet.isReachable(1000)) {
                result = false;
            }
        } catch (IOException e) {
            result = false;
        }
        return  result;
    }

    @Transactional
    void checkActualTasks() {
        List<Task> lt = t.findAllByActual(true);
        for (Task t: lt) {
            List<SubTask> lst = st.findAllByTaskAndComplete(t, false);
            if (lst.size() == 0) {
                t.setActual(false);
            }
        }
        t.saveAll(lt);
    }

    @Async
    @Transactional
    public void sendEmailResultCompleteSubTasks() {
        List<SubTask> list = st.findAllByWorkAndComplete(true, true);
        for (SubTask l: list) {
            l.setWork(false);
            st.save(l);
            if (l.getTask().getSellist4() == ToolHandlers.email) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo("telesyn73@mail.ru");
                message.setSubject(String.format("Result subtask №%s of task №%s", l.getId(), l.getTask().getId()));
                String res = l.getTask().toString() + System.lineSeparator();
                res += l.toString() + System.lineSeparator();
                message.setText(res);
                mail.send(message);
            }
        }
    }


    @Async
    @Transactional
    public void runSubTask(List<SubTask> list) {
        ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);
        checkActualTasks();
        for (SubTask l : list) {
            if (l.getDate1().getTime() > System.currentTimeMillis()) {
                scheduler.schedule(new Runnable() {
                                       @SneakyThrows
                                       @Override
                                       public void run() {
                                           boolean Successfully = false;
                                           Task task = l.getTask();
                                           if (isCorrectHost(task.getText2())) {
                                                    chooseTool ct = new chooseTool();
                                                    l.setResult(ct.getResultWithTools(task).getBytes());
                                                    Successfully = true;
                                           } else {
                                               l.setResult("Host not found".getBytes());
                                           }
                                           l.setComplete(true);
                                           l.setSuccessfully(Successfully);
                                           l.setWork(true);
                                           st.save(l);
                                       }
                                   },
                        new Date(l.getDate1().getTime()));
                l.setWork(true);
            } else {
                l.setResult("Dont work this subtask in time".getBytes());
                l.setComplete(true);
                l.setWork(true);
            }
        }
        st.saveAll(list);
    }
}
