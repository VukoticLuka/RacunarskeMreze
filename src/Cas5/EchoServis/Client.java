package Cas5.EchoServis;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Client {
    private final static String hostname = "localhost";

    public static void main(String[] args) {
        try(Socket socket = new Socket(hostname,Server.PORT_NUM);
            BufferedWriter networkOut = new BufferedWriter(new OutputStreamWriter
                    (socket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader networkIn = new BufferedReader(new InputStreamReader
                    (socket.getInputStream(),StandardCharsets.UTF_8));
            BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in)))
        {
            while (true){
                String line = sysIn.readLine();
                if(line.trim().equalsIgnoreCase("exit"))
                    break;

                networkOut.write(line);
                networkOut.newLine();
                networkOut.flush();


                System.out.println("Echo server response is: " + networkIn.readLine());
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.err.println("Disconected from server " + hostname);
    }
}
