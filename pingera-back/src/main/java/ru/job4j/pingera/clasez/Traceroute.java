package ru.job4j.pingera.clasez;

import org.icmp4j.IcmpPingResponse;

import java.net.UnknownHostException;
import java.util.Map;

public interface Traceroute {
    void setIp(String host) throws UnknownHostException;
    void setPacketsize(int packetsize);
    void setTTL(int ttl);
    void setTimeOut(long ttl);

    Map<Integer, IcmpPingResponse> traceroute();
    String reportTraceroute(Map<Integer, IcmpPingResponse> list);
}
