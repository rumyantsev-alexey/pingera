package ru.job4j.pingera.clasez;

import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;

@Getter
@Setter
abstract class ResultOfNetworkTools {
    InetAddress adr;
    boolean Success;
    String result;

}
