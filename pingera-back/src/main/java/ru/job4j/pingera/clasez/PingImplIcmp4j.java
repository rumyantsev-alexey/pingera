package ru.job4j.pingera.clasez;

import org.icmp4j.IcmpPingRequest;
import org.icmp4j.IcmpPingResponse;
import org.icmp4j.IcmpPingUtil;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.List;

@Component
public class PingImplIcmp4j implements Ping {
    private IcmpPingRequest ip = new IcmpPingRequest();
    private int count = 2;

    @Override
    public void setIp(InetAddress host) {
        ip.setHost(host.getHostAddress());
    }

    @Override
    public void setIp(String host) {
        ip.setHost(host);
    }

    @Override
    public void setCount(int cnt) {
        this.count = cnt + 1;
    }

    @Override
    public void setPacketsize(int packetsize) {
        ip.setPacketSize(packetsize);
    }

    @Override
    public void setTTL(int ttl) {
        ip.setTtl(ttl);
    }

    @Override
    public void setTimeOut(long timeout) {
        ip.setTimeout(timeout);
    }

    @Override
    public PingType ping() {
        List<IcmpPingResponse> res = IcmpPingUtil.executePingRequests(ip, this.count < 1? 2: this.count);
        return new PingTypeImplIcmp4j(res);
    }

    @Override
    public PingType ping(InetAddress ip4) {
        this.setIp(ip4);
        List<IcmpPingResponse> res = IcmpPingUtil.executePingRequests(ip, this.count < 1? 2: this.count);
        return new PingTypeImplIcmp4j(res);
    }

    @Override
    public PingType ping(InetAddress ip4, int count) {
        this.setIp(ip4);
        List<IcmpPingResponse> res = IcmpPingUtil.executePingRequests(ip, count < 0 ? 2: count + 1);
        return new PingTypeImplIcmp4j(res);
    }

    @Override
    public PingType ping(InetAddress ip4, int count, int packetsize) {
        this.setIp(ip4);
        this.setPacketsize(packetsize);
        List<IcmpPingResponse> res = IcmpPingUtil.executePingRequests(ip, count < 0 ? 2: count + 1);
        return new PingTypeImplIcmp4j(res);
    }

    @Override
    public PingType ping(InetAddress ip4, int count, int packetsize, int ttl) {
        this.setIp(ip4);
        this.setPacketsize(packetsize);
        this.setTTL(ttl);
        List<IcmpPingResponse> res = IcmpPingUtil.executePingRequests(ip, count < 0 ? 2: count + 1);
        return new PingTypeImplIcmp4j(res);
    }

    @Override
    public PingType ping(InetAddress ip4, int count, int packetsize, int ttl, long timeout) {
        this.setIp(ip4);
        this.setPacketsize(packetsize);
        this.setTTL(ttl);
        this.setTimeOut(timeout);
        List<IcmpPingResponse> res = IcmpPingUtil.executePingRequests(ip, count < 0 ? 2: count + 1);
        return new PingTypeImplIcmp4j(res);
    }
}
