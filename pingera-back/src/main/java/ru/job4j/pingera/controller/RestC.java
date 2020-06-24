package ru.job4j.pingera.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.job4j.pingera.clasez.Ping;
import ru.job4j.pingera.clasez.PingType;
import ru.job4j.pingera.clasez.PingTypeImplIcmp4j;
import ru.job4j.pingera.dto.UserDto;
import ru.job4j.pingera.models.User;
import ru.job4j.pingera.repositories.UsersRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;

@RestController
@CrossOrigin("*")
public class RestC {

    @Autowired
    Ping ppp;

    @Autowired
    private UsersRepository usdb;

    @GetMapping(value = "/rest/ping")
    public PingType getPing(@RequestParam(value = "host") String host, @RequestParam(value = "count", required = false, defaultValue = "4") int count, @RequestParam(value = "packetsize", required = false, defaultValue = "32") int packetsize, @RequestParam(value = "ttl", required = false, defaultValue = "53") int ttl, @RequestParam(value = "timeout", required = false, defaultValue = "53") long timeout) {
        PingType res = new PingTypeImplIcmp4j();
        count = count < 1 ? 1 : count;
        packetsize = packetsize < 1 ? 32 : packetsize;
        ttl = ttl < 1 ? 53 : ttl;
        timeout = timeout < 1 ? -1 : timeout;
        ppp.setPacketsize(packetsize);
        ppp.setTTL(ttl);
        ppp.setTimeOut(timeout);
        ppp.setIp(host);
        res = ppp.ping();
        return  res;
    }

    @PostMapping("/login")
    public boolean login(@RequestBody UserDto user) {
        User usertemp = usdb.findByName(user.getName());
        return usertemp != null;
    }

    @PostMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring(5).trim();
        return () ->  new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }

}
