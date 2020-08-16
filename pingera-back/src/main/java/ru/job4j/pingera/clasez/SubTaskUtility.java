package ru.job4j.pingera.clasez;

import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.pingera.ApplicationStartup;
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


    private static final Logger LOG = LogManager.getLogger(SubTaskUtility.class.getName());

    @Autowired
    private SubTaskRepository st;

    @Autowired
    private TasksRepository t;

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
        for(int i =1 ; i <= task.getTotal(); i++) {
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
    public void runSubTask(List<SubTask> list) {
        ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);
        for (SubTask l : list) {
            if (l.getDate1().getTime() > System.currentTimeMillis()) {
                scheduler.schedule(new Runnable() {
                                        @Override
                                       public void run() {
                                           boolean successfully = false;
                                           Task task = l.getTask();
                                           if (isCorrectHost(task.getText2())) {
                                                    SelectTool ct = new SelectTool();
                                                    ResultOfNetworkTools r = ct.getResultWithTools(task);
                                                    l.setResult(r.getResult().getBytes());
                                                    successfully = r.isSuccess();
                                           } else {
                                               l.setResult("Host not found".getBytes());
                                           }
                                           l.setComplete(true);
                                           l.setSuccessfully(successfully);
                                           l.setWork(true);
                                           st.save(l);
                                       }
                                   },
                        new Date(l.getDate1().getTime()));
                l.setWork(true);
                LOG.info(String.format("Set subtask № %s  task № %s for execution in %s", l.getId(), l.getTask().getId(), new Date(l.getDate1().getTime())));
            } else {
                l.setResult("Dont work this subtask because the time is up".getBytes());
                l.setComplete(true);
                l.setWork(true);
                LOG.info(String.format("Subtask № %s  task № %s dont run because the time is up", l.getId(), l.getTask().getId()));
            }
        }
        st.saveAll(list);
        checkActualTasks();
    }
}
