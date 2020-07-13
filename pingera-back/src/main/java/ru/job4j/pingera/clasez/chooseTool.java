package ru.job4j.pingera.clasez;

import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.pingera.models.Task;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class chooseTool {
       public ResultOfNetworkTools getResultWithTools(Task task) {
        ResultOfNetworkTools result = new ResultPingImplIcmp4j();
        switch (task.getSellist1()) {
            case ping:
                ResultPingType p = new ResultPingTypeImplIcmp4J();
                ResultPingImplIcmp4j ping = new ResultPingImplIcmp4j();
                try {
                    p = ping.doit(InetAddress.getByName(task.getText2()), task.getCnt(), task.getPacketsize(), task.getTtl(), task.getTimeout());
                    result.setResult(p.toString());
                } catch (UnknownHostException e) {
                    result.setResult("Unknown Host");
                } finally {
                    result.setSuccess(p.isCorrect());
                }
                break;
            case traceroute:
                ResultTracerouteType ptr = new ResultTracerouteTypeImplIcmp4J();
                ResultTracerouteImplIcmp4j tr = new ResultTracerouteImplIcmp4j();
                try {
                    ptr = tr.doit(InetAddress.getByName(task.getText2()), task.getPacketsize(), task.getTtl(), task.getTimeout());
// todo ip
                    result.setResult(ptr.toString());
                } catch (UnknownHostException e) {
                    result.setResult("Unknown Host");
                } finally {
                    result.setSuccess(ptr.isCorrect());
                }
                break;
            default:
                result.setResult("Tool not found");
                break;
        }
        return result;
    }
}
