package ru.job4j.pingera.clasez;

import org.icmp4j.IcmpPingRequest;
import org.icmp4j.IcmpPingUtil;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.ArrayList;

@Component
public class PingImplIcmp4j implements DoIt {
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
    public ResultOfNetworkTools doit() {
        ResultPingTypeImplIcmp4J res = new ResultPingTypeImplIcmp4J();
        res.setRes(IcmpPingUtil.executePingRequests(ip, this.count < 1? 2: this.count));
        return res;
    }

    @Override
    public ResultOfNetworkTools doit(InetAddress ipa, int... op) {
        ResultPingTypeImplIcmp4J res = new ResultPingTypeImplIcmp4J();
        switch (op.length) {
            case 0:
                this.setIp(ipa);
                res.setRes(IcmpPingUtil.executePingRequests(ip, this.count < 1? 2: this.count));
                break;
            case 1:
                this.setIp(ipa);
                this.setCount(op[0]);
                res.setRes(IcmpPingUtil.executePingRequests(ip, this.count < 1? 2: this.count));
                break;
            case 2:
                this.setIp(ipa);
                this.setCount(op[0]);
                this.setPacketsize(op[1]);
                res.setRes(IcmpPingUtil.executePingRequests(ip, this.count < 1? 2: this.count));
                break;
            case 3:
                this.setIp(ipa);
                this.setCount(op[0]);
                this.setPacketsize(op[1]);
                this.setTTL(op[2]);
                res.setRes(IcmpPingUtil.executePingRequests(ip, this.count < 1? 2: this.count));
                break;
            case 4:
                this.setIp(ipa);
                this.setCount(op[0]);
                this.setPacketsize(op[1]);
                this.setTTL(op[2]);
                this.setTimeOut(op[3]);
                res.setRes(IcmpPingUtil.executePingRequests(ip, this.count < 1? 2: this.count));
                break;
            default:
                res.setRes(new ArrayList<>());
                break;
        }
//        res.setSuccess(res.getRes()[0].);
        return res;
    }

}
