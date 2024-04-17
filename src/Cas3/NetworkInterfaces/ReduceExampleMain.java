package Cas3.NetworkInterfaces;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReduceExampleMain {
    public static void main(String[] args) {
        List<Integer> lista = new ArrayList<>();

        try(Scanner sc = new Scanner(System.in)){
            while (sc.hasNextInt()){
                lista.add(0,sc.nextInt());
            }
        }

        int sum = lista.stream().reduce(0, (x,y) -> x+y);
        System.out.println("Suma: " + sum);

        //primer pronalazenja max

        int max = lista.stream().reduce(Integer.MIN_VALUE, (x,y) -> x > y ? x : y);

        System.out.println("Maximalni elem: " + max);


        InetAddress addr = InetAddress.getLoopbackAddress();
        System.out.println(addr.getHostAddress());


    }
}
