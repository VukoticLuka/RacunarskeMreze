package Cas3.Intro;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAdresses {
    public static void main(String[] args) {
        try {
            InetAddress dnsAdress = InetAddress.getByName("www.math.rs");
            System.out.println("Ip adress of DNS is: " + dnsAdress.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
