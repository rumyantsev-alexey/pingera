package ru.job4j.pingera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.job4j.pingera.clasez.ConcurrentSubTask;

import java.util.Date;

@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ConcurrentSubTask cst;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;


    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        cst.initShedulerSubTasks();
        System.out.println("Run initShedulerSubTasks()");
        System.out.println("Time:" + new Date(System.currentTimeMillis()));

        taskScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                cst.everTimeShedulerSubTasks();
                System.out.println("Run everTimeShedulerSubTasks()");
                System.out.println("Time:" + new Date(System.currentTimeMillis()));
            }
        }, new Date(System.currentTimeMillis() + 60000), 2000);

    }

}
