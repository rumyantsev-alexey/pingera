package ru.job4j.pingera.clasez;

import lombok.SneakyThrows;
import ru.job4j.pingera.models.Task;

import java.net.InetAddress;

public class chooseTool {

    @SneakyThrows
    public String getResultWithTools(Task task) {
        String result = "";
        switch (task.getSellist1()) {
            case ping:
                PingType p = new PingImplIcmp4j().ping(InetAddress.getByName(task.getText2()), task.getCnt(), task.getPacketsize(), task.getTtl(), task.getTimeout());
                result = p.toString();
                break;
            default:
                result = "Tool not found";
                break;
        }
        return result;
    }
}
