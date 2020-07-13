package ru.job4j.pingera.clasez;

import lombok.Data;

import java.net.InetAddress;

@Data
abstract class ResultOfNetworkTools<K> {
    InetAddress adr;
    boolean Success;
    String result;
    K result2;

    public abstract void setIp(InetAddress host);
    public abstract void setIp(String host);
    public abstract void setCount(int cnt);
    public abstract void setPacketsize(int packetsize);
    public abstract void setTTL(int ttl);
    public abstract void setTimeOut(long timeout);

    public abstract K doit();
    public abstract K doit(InetAddress ip, int ... op);
}
