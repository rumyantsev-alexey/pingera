package ru.job4j.pingera.clasez;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.pingera.models.SubTask;
import ru.job4j.pingera.models.Task;
import ru.job4j.pingera.repositories.SubTaskRepository;
import ru.job4j.pingera.repositories.TasksRepository;

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

    ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();

    @Transactional
    @Async
    public void initShedulerSubTasks() {
        ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);
        List<SubTask> list = st.findAllByComplete(false);
        for (SubTask l : list) {
//            st.delete(l);
            scheduler.schedule(new Runnable() {
                                   @SneakyThrows
                                   @Override
                                   public void run() {
                                       Task task = l.getTask();
                                       PingType p = new PingImplIcmp4j().ping(InetAddress.getByName(task.getText2()), task.getCnt(), task.getPacketsize(), task.getTtl(), task.getTimeout());
                                       l.setResult(new javax.sql.rowset.serial.SerialClob(p.toString().toCharArray()));
                                       l.setComplete(true);
                                   }
                               },
                    new Date(l.getDate1().getTime()));
            l.setWork(true);
        }
        st.saveAll(list);
    }

    @Transactional
    @Async
    public void everTimeShedulerSubTasks() {
        ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);
        List<SubTask> list = st.findAllByWorkAndComplete(false, false);
        for (SubTask l: list) {
//            st.delete(l);
            scheduler.schedule(new Runnable() {
                                   @SneakyThrows
                                   @Override
                                   public void run() {
                                        Task task = l.getTask();
                                        PingType p = new PingImplIcmp4j().ping(InetAddress.getByName(task.getText2()), task.getCnt(), task.getPacketsize(), task.getTtl(), task.getTimeout());
                                        l.setResult(new javax.sql.rowset.serial.SerialClob(p.toString().toCharArray()));
                                        l.setComplete(true);
                                        if (task.isSplit() && st.findAllByTaskAndComplete(task, true).size() > 0 && st.findAllByTaskAndComplete(task, false).size() == 0) {
                                            task.setActual(false);
//                                            t.deleteById(task.getId());
                                            t.save(task);
                                        }
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

}