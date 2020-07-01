package ru.job4j.pingera.clasez;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Component;
import ru.job4j.pingera.models.SubTask;
import ru.job4j.pingera.models.Task;
import ru.job4j.pingera.repositories.SubTaskRepository;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class ConcurrentSubTask {

    @Autowired
    private SubTaskRepository st;

    ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();

    @Async
    public void everTimeShedulerSubTasks() {
        ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);
        List<SubTask> list = (List<SubTask>) st.findAllByWorkAndComplete(false, false);
        for (SubTask l: list) {
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

    @Async
    public void initShedulerSubTasks() {
        ConcurrentTaskScheduler scheduler = new ConcurrentTaskScheduler(localExecutor);
        List<SubTask> list = st.findAllByComplete(false);
        for (SubTask l: list) {
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

}
