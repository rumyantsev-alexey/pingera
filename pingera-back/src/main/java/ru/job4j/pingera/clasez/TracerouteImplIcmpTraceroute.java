package ru.job4j.pingera.clasez;


import org.icmp4j.IcmpPingResponse;
import org.icmp4j.IcmpTraceRouteRequest;
import org.icmp4j.IcmpTraceRouteUtil;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;


/**
 * This is what a tracert looks like from the command line:
 * C:\Users\sal>tracert www.google.com
 * Tracing route to www.google.com [74.125.224.52]
 * over a maximum of 30 hops:
 *   1     7 ms     9 ms     1 ms  10.142.222.1
 *   2    10 ms     5 ms     3 ms  10.0.0.1
 *   3    12 ms    10 ms     9 ms  10.6.44.1
 *   4    28 ms    14 ms    15 ms  ip68-4-12-22.oc.oc.cox.net [68.4.12.22]
 *   5    24 ms    30 ms    98 ms  ip68-4-11-98.oc.oc.cox.net [68.4.11.98]
 *   6    22 ms    19 ms    19 ms  68.1.5.137
 *   7    18 ms    19 ms    19 ms  langbbrj01-ge050000804.r2.la.cox.net [68.105.30.181]
 *   8    17 ms    29 ms    21 ms  216.239.46.40
 *   9    22 ms    27 ms    20 ms  209.85.252.149
 *  10    28 ms    19 ms    27 ms  lax17s01-in-f20.1e100.net [74.125.224.52]
 * Trace complete.
 *
 * This is what a tracert looks like with this library:
 *     [junit] response: [
 *     [junit] ttl: 1, response: [hashCode: 15186923, successFlag: false, timeoutFlag: false, errorMessage: IP_TTL_EXPIRED_TRANSIT, throwable: null, host: 10.142.222.1, size: 0, rtt: 3, ttl: 64]
 *     [junit] ttl: 2, response: [hashCode: 31615954, successFlag: false, timeoutFlag: false, errorMessage: IP_TTL_EXPIRED_TRANSIT, throwable: null, host: 10.0.0.1, size: 0, rtt: 13, ttl: 63]
 *     [junit] ttl: 3, response: [hashCode: 1367891, successFlag: false, timeoutFlag: false, errorMessage: IP_TTL_EXPIRED_TRANSIT, throwable: null, host: 10.6.44.1, size: 0, rtt: 23, ttl: 253]
 *     [junit] ttl: 4, response: [hashCode: 5370470, successFlag: false, timeoutFlag: false, errorMessage: IP_TTL_EXPIRED_TRANSIT, throwable: null, host: 68.4.12.20, size: 0, rtt: 23, ttl: 252]
 *     [junit] ttl: 5, response: [hashCode: 73029, successFlag: false, timeoutFlag: false, errorMessage: IP_TTL_EXPIRED_TRANSIT, throwable: null, host: 68.4.11.96, size: 0, rtt: 27, ttl: 251]
 *     [junit] ttl: 6, response: [hashCode: 19446204, successFlag: false, timeoutFlag: false, errorMessage: IP_TTL_EXPIRED_TRANSIT, throwable: null, host: 68.1.0.136, size: 0, rtt: 33, ttl: 247]
 *     [junit] ttl: 7, response: [hashCode: 12193604, successFlag: false, timeoutFlag: false, errorMessage: IP_TTL_EXPIRED_TRANSIT, throwable: null, host: 68.105.30.181, size: 0, rtt: 33, ttl: 247]
 *     [junit] ttl: 8, response: [hashCode: 20995753, successFlag: false, timeoutFlag: false, errorMessage: IP_TTL_EXPIRED_TRANSIT, throwable: null, host: 64.233.174.238, size: 0, rtt: 29, ttl: 248]
 *     [junit] ttl: 9, response: [hashCode: 17219963, successFlag: false, timeoutFlag: false, errorMessage: IP_TTL_EXPIRED_TRANSIT, throwable: null, host: 72.14.236.13, size: 0, rtt: 33, ttl: 247]
 *     [junit] ttl: 10, response: [hashCode: 8947790, successFlag: true, timeoutFlag: false, errorMessage: SUCCESS, throwable: null, host: 74.125.224.212, size: 32, rtt: 28, ttl: 55]
 *     [junit] ttl: 11, response: [hashCode: 28106261, successFlag: true, timeoutFlag: false, errorMessage: SUCCESS, throwable: null, host: 74.125.224.212, size: 32, rtt: 31, ttl: 55]
 *     [junit] ttl: 12, response: [hashCode: 2651170, successFlag: true, timeoutFlag: false, errorMessage: SUCCESS, throwable: null, host: 74.125.224.212, size: 32, rtt: 30, ttl: 55]
 *     [junit] ttl: 13, response: [hashCode: 31485310, successFlag: true, timeoutFlag: false, errorMessage: SUCCESS, throwable: null, host: 74.125.224.212, size: 32, rtt: 29, ttl: 55]
 *     [junit] ttl: 14, response: [hashCode: 20216452, successFlag: true, timeoutFlag: false, errorMessage: SUCCESS, throwable: null, host: 74.125.224.212, size: 32, rtt: 31, ttl: 55]
 *     [junit] ttl: 15, response: [hashCode: 5746246, successFlag: true, timeoutFlag: false, errorMessage: SUCCESS, throwable: null, host: 74.125.224.212, size: 32, rtt: 17, ttl: 55]
 *     [junit] ttl: 16, response: [hashCode: 7514401, successFlag: true, timeoutFlag: false, errorMessage: SUCCESS, throwable: null, host: 74.125.224.212, size: 32, rtt: 32, ttl: 55]
 *     ...
 *     [junit] ttl: 29, response: [hashCode: 29892897, successFlag: true, timeoutFlag: false, errorMessage: SUCCESS, throwable: null, host: 74.125.224.212, size: 32, rtt: 29, ttl: 55]
 *     [junit] ttl: 30, response: [hashCode: 32973925, successFlag: true, timeoutFlag: false, errorMessage: SUCCESS, throwable: null, host: 74.125.224.212, size: 32, rtt: 29, ttl: 55]
 *     [junit] ]
 */
@Component
public class TracerouteImplIcmpTraceroute implements Traceroute {

    private InetAddress ip = InetAddress.getLoopbackAddress();
    private IcmpTraceRouteRequest ipr = new IcmpTraceRouteRequest();

    {
        ipr.setHost(InetAddress.getLoopbackAddress().toString());
    }

    @Override
    public void setIp(String host) throws UnknownHostException {
        this.ip = InetAddress.getByName(host);
        this.ipr.setHost(host);
    }

    @Override
    public void setPacketsize(int packetsize) {
        this.ipr.setPacketSize(packetsize);
    }

    @Override
    public void setTTL(int ttl) {
        this.ipr.setTtl(ttl);
    }

    @Override
    public void setTimeOut(long ttl) {
        this.ipr.setTimeout(ttl);
    }

    @Override
    public Map<Integer, IcmpPingResponse> traceroute() {
        Map<Integer, IcmpPingResponse> list;
        list = IcmpTraceRouteUtil.executeTraceRoute(ipr).getTtlToResponseMap();
        return list;
    }

    @Override
    public String reportTraceroute(Map<Integer, IcmpPingResponse> list) {
        String header = "Tracing route to %s [%s]\n"
                + "over a maximum of 30 hops:\n\n";
        String body = "%s  %s ms     %s ms     %s ms  %s [%s]\n";
        String footer = "\nTrace complete.";
        StringBuilder result = new StringBuilder();
        String host = list.get(list.size()).getHost();
        result.append(String.format(header, ip.getHostName(), ip.getHostAddress()));
        for (Integer i: normal(list).keySet()) {
            InetAddress a = InetAddress.getLoopbackAddress();
            try {
                a = InetAddress.getByName(list.get(i).getHost());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            result.append(String.format(body, i, list.get(i).getDuration(), list.get(i).getDuration(), list.get(i).getDuration(), a.getHostName(), a.getHostAddress()));
        }
        result.append(footer);
        return result.toString();
    }

    private Map<Integer, IcmpPingResponse> normal(Map<Integer, IcmpPingResponse> list) {
        Map<Integer, IcmpPingResponse> result = new TreeMap<>();
        for (Integer i: list.keySet()) {
            IcmpPingResponse a = list.get(i);
            result.put(i, a);
            if (a.getSuccessFlag()) {
                break;
            }
        }
        return result;
    }
}
