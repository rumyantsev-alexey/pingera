package ru.job4j.pingera.clasez;

import ru.job4j.pingera.models.SubTask;
import ru.job4j.pingera.models.Task;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ConvertTaskToSubtasks {

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
            st.setAt_work(false);
            st.setComplete(false);
            result.add(st);
        }
        return result;
    }
}
