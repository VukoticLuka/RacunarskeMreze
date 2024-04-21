package Cas5.DayTimeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost";
        System.err.println("Connecting to: " + hostname);

        try(Socket socket = new Socket(hostname,Server.PORT_NUM)){
            System.out.println("Connected!");
            System.out.println(socket.getInetAddress());

            try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
                String dayTime = in.readLine().trim();
                System.out.println("It is: " + dayTime + " at " + hostname);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
