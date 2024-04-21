package Cas5.Intro;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class PortScanner {

    private static String host = "localhost";
    public static void main(String[] args) {



        System.out.println("Start time: " + new Date());

        for(int port = 1; port < Short.MAX_VALUE*2+2; port++){
            System.out.printf("\rTesting port: %5d",port);

            try(Socket s = new Socket(host,port)){
                System.out.println("\rSocket data: " + s);
                System.out.println("Found @ " + new Date());

            }catch (UnknownHostException e){
                e.printStackTrace();
                break;
            }catch (IOException e){
                //ignore
            }
        }
    }
}
