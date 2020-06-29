package ru.job4j.pingera.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.job4j.pingera.clasez.Ping;
import ru.job4j.pingera.clasez.PingType;
import ru.job4j.pingera.clasez.PingTypeImplIcmp4j;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@CrossOrigin("*")
public class RestControler {

    @Autowired
    Ping ppp;

    @GetMapping(value = "/rest/ping")
    public PingType getPing(@RequestParam(value = "host") String host, @RequestParam(value = "count", required = false, defaultValue = "4") int count, @RequestParam(value = "packetsize", required = false, defaultValue = "32") int packetsize, @RequestParam(value = "ttl", required = false, defaultValue = "53") int ttl, @RequestParam(value = "timeout", required = false, defaultValue = "53") long timeout) {
        InetAddress hhh = null;
        try {
            hhh = Inet4Address.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
        PingType res = new PingTypeImplIcmp4j();
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
        res = ppp.ping();
        if (!res.isCorrect()) {
            res = null;
        }
        return  res;
    }

    @GetMapping(value = "/rest/pingtostring")
    public String getStringFromPing(@RequestParam(value = "host") String host, @RequestParam(value = "count", required = false, defaultValue = "4") int count, @RequestParam(value = "packetsize", required = false, defaultValue = "32") int packetsize, @RequestParam(value = "ttl", required = false, defaultValue = "53") int ttl, @RequestParam(value = "timeout", required = false, defaultValue = "53") long timeout) throws JsonProcessingException {
        InetAddress hhh = null;
        try {
            hhh = Inet4Address.getByName("localhost");
        } catch (UnknownHostException e) {
            String jsonString = new ObjectMapper().writeValueAsString("Localhost not found");
            return jsonString;
        }
        PingType res = new PingTypeImplIcmp4j();
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
        res = ppp.ping();
        if (!res.isCorrect()) {
            res = null;
        }
        String jsonString = new ObjectMapper().writeValueAsString(res.toString());
        return jsonString;
    }
}
