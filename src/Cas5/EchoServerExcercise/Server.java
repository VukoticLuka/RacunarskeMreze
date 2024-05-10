package Cas5.EchoServerExcercise;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT_NUMBER = 9876;
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PORT_NUMBER)) {
            System.err.println("Server bound to port: " + PORT_NUMBER);

            while (true){
                System.err.println("Listening for clients...");
                Socket client = server.accept();

                System.err.println("Client accepted! Dispatching thread...");

                new Thread(new ClientHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
