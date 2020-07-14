package ru.job4j.pingera.clasez;

import java.net.InetAddress;

public interface DoIt {
    void setIp(InetAddress host);
    void setIp(String host);
    void setCount(int cnt);
    void setPacketsize(int packetsize);
    void setTTL(int ttl);
    void setTimeOut(long timeout);

    ResultOfNetworkTools doit();
    ResultOfNetworkTools doit(InetAddress ip, int... op);
}
