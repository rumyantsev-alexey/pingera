package ru.job4j.pingera.clasez;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.pingera.models.Task;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SelectTool {

    private static final Logger LOG = LogManager.getLogger(SelectTool.class.getName());

    public ResultOfNetworkTools getResultWithTools(Task task) {
        ResultOfNetworkTools result = new ResultPingTypeImplIcmp4J();
           LOG.info(String.format("Task № %s uses %s for result ",task.getId(), task.getSellist1()));
           switch (task.getSellist1()) {
            case ping:
                ResultOfNetworkTools p = new ResultPingTypeImplIcmp4J();
                DoIt ping = new PingImplIcmp4j();
                try {
                    p = ping.doit(InetAddress.getByName(task.getText2()), task.getCnt(), task.getPacketsize(), task.getTtl(), task.getTimeout());
                    result.setResult(p.toString());
                    result.setSuccess(true);
                } catch (UnknownHostException e) {
                    result.setResult("Unknown Host");
                    result.setSuccess(false);
                }
                break;
            case traceroute:
                ResultOfNetworkTools ptr = new ResultTracerouteTypeImplIcmp4J();
                DoIt tr = new TracerouteImplIcmp4j();
                try {
                    ptr = tr.doit(InetAddress.getByName(task.getText2()),0, task.getPacketsize(), task.getTtl(), task.getTimeout());
                    result.setResult(ptr.toString());
                    result.setSuccess(true);
                } catch (UnknownHostException e) {
                    result.setResult("Unknown Host");
                    result.setSuccess(false);
                }
                break;
            default:
                result.setResult("Tool not found");
                break;
        }
        return result;
    }
}
