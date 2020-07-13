package ru.job4j.pingera.clasez;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.icmp4j.IcmpPingResponse;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;

@NoArgsConstructor
public class ResultTracerouteTypeImplIcmp4J implements ResultTracerouteType {

    private static final int traceroute_count = 30;

    public ResultTracerouteTypeImplIcmp4J(Map<Integer, IcmpPingResponse> map) {
        this.res = map;
    }

    @Getter
    @Setter
    private  InetAddress ip = InetAddress.getLoopbackAddress();

    private Map<Integer, IcmpPingResponse> res = new TreeMap<>();

    @Override
    public boolean isCorrect() {
        boolean result = false;
        try {
            result = res.get(0).getSuccessFlag();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            result = false;
        }
        finally {
            return result;
        }
    }


    public String toString() {
        String header = "Tracing route to %s [%s]" + System.lineSeparator()
                + "over a maximum of 30 hops:" + System.lineSeparator() + System.lineSeparator();
        String body = "%s  %s ms     %s ms     %s ms  %s [%s]" + System.lineSeparator();
        String footer =  System.lineSeparator() + "Trace complete.";
        StringBuilder result = new StringBuilder();
        result.append(String.format(header, ip.getHostName(), ip.getHostAddress()));
        int ii = 0;
        for (Integer i: normal(res).keySet()) {
            ii++;
            if (ii > traceroute_count) {
                break;
            }
            InetAddress a = InetAddress.getLoopbackAddress();
            try {
                a = InetAddress.getByName(res.get(i).getHost());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            result.append(String.format(body, i, res.get(i).getDuration(), res.get(i).getDuration(), res.get(i).getDuration(), a.getHostName(), a.getHostAddress()));
        }
        result.append(footer);
        return result.toString();
    }

    private Map<Integer, IcmpPingResponse> normal(Map<Integer, IcmpPingResponse> list) {
        Map<Integer, IcmpPingResponse> result = new TreeMap<>();
        for (Integer i : list.keySet()) {
            IcmpPingResponse a = list.get(i);
            result.put(i, a);
            if (a.getSuccessFlag()) {
                break;
            }
        }
        return result;
    }

}
