package Cas5.EchoServis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public final static int PORT_NUM = 8765;

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PORT_NUM)){
            System.out.println("Server bound to port: " + PORT_NUM);

            while(true){
                System.err.println("Listening for clients...");

                Socket client = server.accept();
                //every client will be handled in separated thread
                System.err.println("Client accepted! Dispatching thread...");
                new Thread(new ClientHandlerRunnable(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
