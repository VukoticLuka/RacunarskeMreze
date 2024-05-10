package Rokovi.Jan1_2023.Zadatak2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class Server {
    public static final int PORT_NUM = 12321;
    public static final String HOST = "localhost";

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PORT_NUM)){
            System.err.println("Listening for clients");

            while(true){
                Socket client = server.accept();
                System.err.println("Client accepted");
                new Thread(new ClientHandlerRunnable(client)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
