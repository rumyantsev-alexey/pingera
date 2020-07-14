package ru.job4j.pingera.clasez;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.icmp4j.IcmpPingResponse;

import java.net.InetAddress;
import java.util.Map;
import java.util.TreeMap;

@NoArgsConstructor
public class ResultTracerouteTypeImplIcmp4J extends ResultOfNetworkTools {

    @Setter
    private  InetAddress ip = InetAddress.getLoopbackAddress();

    @Setter
    private Map<Integer, IcmpPingResponse> res = new TreeMap<>();

    @SneakyThrows
    public String toString() {
        String header = "Tracing route to %s [%s]" + System.lineSeparator()
                + "over a maximum of 30 hops:" + System.lineSeparator() + System.lineSeparator();
        String body = "%s  %s ms     %s ms     %s ms  %s [%s]" + System.lineSeparator();
        String bad_body = "%s  *          *         *        %s" + System.lineSeparator();
        String footer = "Trace complete.";
        StringBuilder result = new StringBuilder();
        result.append(String.format(header, ip.getHostName(), ip.getHostAddress()));
        int ii = 0;
        for (Integer i: res.keySet()) {
            ii++;
            if (ii > Constant.TRACEROUTE_COUNT) {
                break;
            }
            if(res.get(i).getErrorMessage().contains(Constant.PART_OF_TIMEOUT_MESSAGE)) {
                result.append(String.format(bad_body, i, "time out"));
            } else {
                IcmpPingResponse cur_hope = res.get(i);
                InetAddress a = InetAddress.getByName(cur_hope.getHost());
                result.append(String.format(body, i, cur_hope.getDuration(), cur_hope.getDuration(), cur_hope.getDuration(),a.getCanonicalHostName(), cur_hope.getHost()));
            }
            if(res.get(i).getErrorMessage().equals(Constant.SUCCESS_MESSAGE)) {
                break;
            }
        }
        result.append(footer);
        return result.toString();
    }

}
