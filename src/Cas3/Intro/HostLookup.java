package Cas3.Intro;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static java.rmi.Naming.lookup;

public class HostLookup {
    public static void main(String[] args) {
        if(args.length > 0){
            for(String arg : args){
                System.out.println(lookup(arg));
            }
        }else {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(System.in));
            System.out.println("Enter IP adresses or host names, enter exit or quit to end session: ");


                try {
                    while(true) {
                        String host = in.readLine();
                        if(host.equalsIgnoreCase("exit") || host.equalsIgnoreCase("quit")){
                            break;
                        }
                        System.out.println(lookup(host));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


        }
    }

    private static String lookup(String host){
        InetAddress node;

        try {
            node = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        if(isHostName(host)){
            return node.getHostAddress();
        }else {
            return node.getHostName();
        }
    }

    private static boolean isHostName(String host){
        //is it IPv6 address
        if(host.indexOf(':') != -1){
            return false;
        }

        // To determine if this is a host name or an IPv4 address, we
        // test the number of parts separated by a period - if there
        // are 4 parts, we still aren't sure if it is a hostname or
        // an address, but if it doesn't have 4 parts then it is a
        // hostname

        if(host.split("\\.").length != 4){
            return true;
        }

        return host.chars().anyMatch(c -> !Character.isDigit(c) && c != '.');
    }
}
