package ru.job4j.pingera;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import ru.job4j.pingera.repositories.TasksRepository;

@Component
public class ApplicationStartup
        implements ApplicationListener<ApplicationReadyEvent> {

@Autowired
private TasksRepository t;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        // here your code ...

    }

}
