package ru.job4j.pingera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.job4j.pingera.clasez.SubTaskUtility;
import ru.job4j.pingera.models.SubTask;
import ru.job4j.pingera.repositories.SubTaskRepository;

import java.util.Date;
import java.util.List;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    private static final int delay_for_web = 60000;
    private static final int period_for_web = 2000;
    private static final int delay_for_mail = 10000;
    private static final int period_for_mail = 300000;

    @Autowired
    private SubTaskUtility cst;

    @Autowired
    private SubTaskRepository st;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;


    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        List<SubTask> list = st.findAllByComplete(false);
        cst.runSubTask(list);
//        System.out.println("Run initShedulerSubTasks() in time " + new Date(System.currentTimeMillis()));

        taskScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<SubTask> list = st.findAllByWorkAndComplete(false, false);
                cst.runSubTask(list);
            }
        }, new Date(System.currentTimeMillis() + delay_for_web), period_for_web);

        taskScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                cst.sendEmailResultCompleteSubTasks();
            }
        }, new Date(System.currentTimeMillis() + delay_for_mail), period_for_mail);

    }

}
