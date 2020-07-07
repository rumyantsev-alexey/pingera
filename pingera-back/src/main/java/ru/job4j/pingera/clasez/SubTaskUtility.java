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

import java.net.InetAddress;
import java.net.UnknownHostException;
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

    @Transactional
    @Async
    public void initShedulerSubTasks() {
        checkActualTasks();
        ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);
        List<SubTask> list = st.findAllByComplete(false);
        for (SubTask l : list) {
            if (l.getDate1().getTime() > System.currentTimeMillis()) {
                scheduler.schedule(new Runnable() {
                                       @SneakyThrows
                                       @Override
                                       public void run() {
                                           Task task = l.getTask();
                                           if (isCorrectHost(task.getText2())) {
                                               PingType p = new PingImplIcmp4j().ping(InetAddress.getByName(task.getText2()), task.getCnt(), task.getPacketsize(), task.getTtl(), task.getTimeout());
                                               l.setResult(p.toString());
                                           } else {
                                               l.setResult("Host not found");
                                           }
                                           l.setComplete(true);
                                           String s = l.getResult();
                                           l.setResult(s.substring(0, s.length() > 255 ? 254 : s.length()));
                                           st.save(l);
                                       }
                                   },
                        new Date(l.getDate1().getTime()));
                l.setWork(true);
                System.out.println(l);
            } else {
                l.setResult("Dont work this subtask in time");
                l.setComplete(true);
            }
        }
        st.saveAll(list);
    }

    @Transactional
    @Async
    public void everTimeShedulerSubTasks() {
        checkActualTasks();
        ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);
        List<SubTask> list = st.findAllByWorkAndComplete(false, false);
        for (SubTask l: list) {
            scheduler.schedule(new Runnable() {
                                   @SneakyThrows
                                   @Override
                                   public void run() {
                                       Task task = l.getTask();
                                       if (isCorrectHost(task.getText2())) {
                                           PingType p = new PingImplIcmp4j().ping(InetAddress.getByName(task.getText2()), task.getCnt(), task.getPacketsize(), task.getTtl(), task.getTimeout());
                                           l.setResult(p.toString());
                                       } else {
                                           l.setResult("Host not found");
                                       }
                                       l.setComplete(true);
                                       String s = l.getResult();
                                       l.setResult(s.substring(0, s.length() > 255 ? 254 : s.length()));
                                       st.save(l);
                                   }
                               },
                    new Date(l.getDate1().getTime()));
            l.setWork(true);
        }
        st.saveAll(list);
    }

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

     boolean isCorrectHost(String name) {
        boolean result = true;
        try {
            InetAddress.getByName(name);
        } catch (UnknownHostException e) {
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
                t.setReport(false);
            }
        }
        t.saveAll(lt);
    }

    @Async
    @Transactional
    public void sendEmailResultCompleteTasks() {
        String result = new String();
        List<Task> list = t.findAllByActualAndReport(false, false);
        for( Task l: list) {
            result = new String();
            List<SubTask> lst = st.findAllByTaskAndComplete(l, true);
            for (SubTask st: lst) {
                result+=System.lineSeparator() + st.getResult();
            }
            l.setReport(true);
            t.save(l);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("telesyn73@mail.ru");
            message.setSubject(String.format("Result of task №%s", l.getId()));
            message.setText(result);
            mail.send(message);
        }
    }

}
