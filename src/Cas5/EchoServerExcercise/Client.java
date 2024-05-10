package Cas5.EchoServerExcercise;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Client {
    public static final String hostname = "localhost";

    public static void main(String[] args) {

        try(Socket socket = new Socket(hostname,Server.PORT_NUMBER)){
            BufferedReader networkOut = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter networkIn = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));


            while(true){
                String msg = sysIn.readLine();

                if(msg.trim().equalsIgnoreCase("exit"))
                    break;

                networkIn.write(msg);
                networkIn.newLine();
                networkIn.flush();


                System.err.println("Server echo response: " + networkOut.readLine());
            }

            sysIn.close();
            networkIn.close();
            networkOut.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.err.println("Disconnected from server " + hostname + " " + Server.PORT_NUMBER);
    }
}
