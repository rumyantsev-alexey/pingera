package ru.job4j.pingera.clasez;

import java.net.InetAddress;

public interface Ping {

    void setIp(InetAddress host);
    void setIp(String host);
    void setCount(int cnt);
    void setPacketsize(int packetsize);
    void setTTL(int ttl);
    void setTimeOut(long timeout);

    PingType ping();
    PingType ping(InetAddress ip);
    PingType ping(InetAddress ip, int count);
    PingType ping(InetAddress ip, int count, int packetsize);
    PingType ping(InetAddress ip, int count, int packetsize, int ttl);
    PingType ping(InetAddress ip, int count, int packetsize, int ttl, long timeout);
}
