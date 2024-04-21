package Cas5.Intro;

import java.io.IOException;
import java.net.Socket;

public class ClientSocketIntro {
    public static void main(String[] args) {
        try(Socket socket = new Socket("www.matf.bg.ac.rs",80)) {
            System.out.println(socket);
            System.out.println("Connecting to: " + socket.getInetAddress());
            System.out.println(socket.getPort());
            System.out.println(socket.getLocalPort());
            System.out.println(socket.getLocalAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
