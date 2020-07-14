package ru.job4j.pingera.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.pingera.clasez.*;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@CrossOrigin("*")
public class RestControler {

    @Autowired
    PingImplIcmp4j ppp;


    @Autowired
    TracerouteImplIcmp4j ttt;

    @GetMapping(value = "/rest/ping")
    public ResultPingTypeImplIcmp4J getPing(@RequestParam(value = "host") String host, @RequestParam(value = "count", required = false, defaultValue = "4") int count, @RequestParam(value = "packetsize", required = false, defaultValue = "32") int packetsize, @RequestParam(value = "ttl", required = false, defaultValue = "53") int ttl, @RequestParam(value = "timeout", required = false, defaultValue = "53") long timeout) {
        InetAddress hhh = null;
        try {
            hhh = Inet4Address.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        ResultPingTypeImplIcmp4J res = new ResultPingTypeImplIcmp4J();
        count = count < 1 ? 1 : count;
        packetsize = packetsize < 1 ? 32 : packetsize;
        ttl = ttl < 1 ? 53 : ttl;
        timeout = timeout < 1 ? -1 : timeout;
        ppp.setCount(count + 1);
        ppp.setPacketsize(packetsize);
        ppp.setTTL(ttl);
        ppp.setTimeOut(timeout);
        try {
            hhh = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        ppp.setIp(host);
        res =  (ResultPingTypeImplIcmp4J) ppp.doit();
        if (!res.isSuccess()) {
            res = null;
        }
        return  res;
    }

    @GetMapping(value = "/rest/pingtostring")
    public String getStringFromPing(@RequestParam(value = "host") String host, @RequestParam(value = "count", required = false, defaultValue = "4") int count, @RequestParam(value = "packetsize", required = false, defaultValue = "32") int packetsize, @RequestParam(value = "ttl", required = false, defaultValue = "53") int ttl, @RequestParam(value = "timeout", required = false, defaultValue = "53") long timeout) throws JsonProcessingException {
        InetAddress hhh = null;
        ResultPingTypeImplIcmp4J res = new ResultPingTypeImplIcmp4J();
        count = count < 1 ? 1 : count;
        packetsize = packetsize < 1 ? 32 : packetsize;
        ttl = ttl < 1 ? 53 : ttl;
        timeout = timeout < 1 ? -1 : timeout;
        ppp.setCount(count + 1);
        ppp.setPacketsize(packetsize);
        ppp.setTTL(ttl);
        ppp.setTimeOut(timeout);
        try {
            hhh = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            String jsonString = new ObjectMapper().writeValueAsString("Host not found");
            return jsonString;
        }
        ppp.setIp(host);
        res = (ResultPingTypeImplIcmp4J) ppp.doit();
        if (!res.isSuccess()) {
            res = null;
        }
        String jsonString = new ObjectMapper().writeValueAsString(res.toString());
        return jsonString;
    }

    @GetMapping (value = "/rest/traceroutetostring")
    public String getTraceroute(@RequestParam(value = "host") String host, @RequestParam(value = "packetsize", required = false, defaultValue = "32") int packetsize, @RequestParam(value = "ttl", required = false, defaultValue = "30") int ttl, @RequestParam(value = "timeout", required = false, defaultValue = "30") long timeout) {
        String res = "";
        ResultTracerouteTypeImplIcmp4J r = new ResultTracerouteTypeImplIcmp4J();
        packetsize = packetsize < 1 ? 32 : packetsize;
        ttl = ttl < 1 ? 30 : ttl;
        timeout = timeout < 1 ? -1 : timeout;
        ttt.setPacketsize(packetsize);
        ttt.setTTL(ttl);
        ttt.setTimeOut(timeout);
        ttt.setIp(host);
        r = (ResultTracerouteTypeImplIcmp4J) ttt.doit();
        return  r.toString();
    }

}
