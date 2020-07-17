package ru.job4j.pingera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import ru.job4j.pingera.clasez.Constant;
import ru.job4j.pingera.clasez.SelectToolHeandler;
import ru.job4j.pingera.clasez.SubTaskUtility;
import ru.job4j.pingera.models.SubTask;
import ru.job4j.pingera.repositories.SubTaskRepository;

import java.util.Date;
import java.util.List;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private SubTaskUtility cst;

    @Autowired
    private SubTaskRepository st;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Autowired
    private SelectToolHeandler sth;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        List<SubTask> list = st.findAllByComplete(false);
        cst.runSubTask(list);

        taskScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<SubTask> list = st.findAllByCompleteAndWork(false, false);
                cst.runSubTask(list);
            }
        }, new Date(System.currentTimeMillis() + Constant.DELAY_FOR_WEB), Constant.PERIOD_FOR_WEB);

        taskScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                List<SubTask> list_task = st.findAllByCompleteAndWork(true, true);
                for (SubTask t: list_task) {
                    sth.getResultWithTools(t);
                    t.setWork(false);
                }
                if (list_task.size() > 0) {
                    st.saveAll(list_task);
                }
            }
        }, new Date(System.currentTimeMillis() + Constant.DELAY_FOR_SEND_RESULT), Constant.PERIOD_FOR_SEND_RESULT);

    }

}
