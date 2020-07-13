package ru.job4j.pingera.clasez;

import lombok.SneakyThrows;
import ru.job4j.pingera.models.Task;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class chooseTool {

       public Result getResultWithTools(Task task) {
        Result result = new Result();
        PingType p = new PingTypeImplIcmp4j();
        switch (task.getSellist1()) {
            case ping:
                try {
                    p = new PingImplIcmp4j().ping(InetAddress.getByName(task.getText2()), task.getCnt(), task.getPacketsize(), task.getTtl(), task.getTimeout());
                    result.setText(p.toString());
                } catch (UnknownHostException e) {
                    result.setText("Unknown Host");
                } finally {
                    result.setResultStatus(p.isCorrect());
                }
                break;
            case traceroute:
                result.setText("Traceroute");
                result.setResultStatus(true);
                break;
            default:
                result.setText("Tool not found");
                break;
        }
        return result;
    }
}
