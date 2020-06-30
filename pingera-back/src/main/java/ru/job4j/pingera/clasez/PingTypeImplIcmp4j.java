package ru.job4j.pingera.clasez;

import lombok.*;
import org.icmp4j.IcmpPingResponse;

import java.net.InetAddress;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class PingTypeImplIcmp4j implements PingType{

    private List<IcmpPingResponse> res;

    public PingTypeImplIcmp4j(List<IcmpPingResponse> res) {
        this.res = res;
    }

    @SneakyThrows
    @Override
    public String toString() {

        IcmpPingResponse one = res.get(0);
        String header = "Pinging %s [%s] with %s bytes of data:" + System.lineSeparator() + System.lineSeparator();
        String body = "Reply from %s: bytes=%s time=%sms TTL=%s" + System.lineSeparator();
        String footer = System.lineSeparator() + "Ping statistics for %s:" + System.lineSeparator()
                + "    Packets: Sent = %s, Received = %s, Lost = %s (%s prc loss)," + System.lineSeparator()
                + "Approximate round trip times in milli-seconds:" + System.lineSeparator()
                + "    Minimum = %sms, Maximum = %sms, Average = %sms" + System.lineSeparator();

        StringBuilder result = new StringBuilder();
        InetAddress ip = InetAddress.getByName(one.getHost());

        int lost = (int) res.stream().filter((x) -> !x.getSuccessFlag()).count();
        int size = res.size();
        int min = (int) one.getDuration();
        int max = (int) one.getDuration();
        int sum = 0;
        for (IcmpPingResponse r: res) {
            sum += r.getDuration();
            if (r.getDuration() < min) {
                min = (int) r.getDuration();
            }
            if (r.getDuration() > max) {
                max = (int) r.getDuration();
            }

        }

        result.append(String.format(header, ip.getHostName(), ip.getHostAddress(), one.getSize()));
        res.forEach((x) -> result.append(String.format(body, ip.getHostAddress(), x.getSize(), x.getDuration(), x.getTtl())));
        result.append(String.format(footer, ip.getHostAddress(), size, size - lost, lost, (lost / size) * 100, min, max, sum / size));
        return result.toString();
    };

    @Override
    public boolean isCorrect() {
        return res.get(0).getSuccessFlag();
    }

}

