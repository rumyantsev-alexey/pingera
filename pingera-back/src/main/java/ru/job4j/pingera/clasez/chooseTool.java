package ru.job4j.pingera.clasez;

import org.springframework.beans.factory.annotation.Autowired;
import ru.job4j.pingera.models.Task;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class chooseTool {
       public ResultOfNetworkTools getResultWithTools(Task task) {
        ResultOfNetworkTools result = new ResultPingTypeImplIcmp4J();
        switch (task.getSellist1()) {
            case ping:
                ResultOfNetworkTools p = new ResultPingTypeImplIcmp4J();
                DoIt ping = new PingImplIcmp4j();
                try {
                    p = ping.doit(InetAddress.getByName(task.getText2()), task.getCnt(), task.getPacketsize(), task.getTtl(), task.getTimeout());
                    result.setResult(p.toString());
                } catch (UnknownHostException e) {
                    result.setResult("Unknown Host");
                } finally {
                    result.setSuccess(p.isSuccess());
                }
                break;
            case traceroute:
                ResultOfNetworkTools ptr = new ResultTracerouteTypeImplIcmp4J();
                DoIt tr = new TracerouteImplIcmp4j();
                try {
                    ptr = tr.doit(InetAddress.getByName(task.getText2()),0, task.getPacketsize(), task.getTtl(), task.getTimeout());
                    result.setResult(ptr.toString());
                } catch (UnknownHostException e) {
                    result.setResult("Unknown Host");
                } finally {
                    result.setSuccess(ptr.isSuccess());
                }
                break;
            default:
                result.setResult("Tool not found");
                break;
        }
        return result;
    }
}
