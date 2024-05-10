package Cas6.p02_chat;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientWriteThread extends Thread{

    private final String username;
    private PrintStream toServer;


    public ClientWriteThread(String username, Socket server){
        this.username = username;

        try {
            //true je za auto flush
            this.toServer = new PrintStream(server.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        //First send user name Server expects that
        this.toServer.println(this.username);

        try(Scanner sc = new Scanner(System.in)){
            String text;
            do{
                System.out.printf("\r[%s]:",this.username);
                text = sc.nextLine();
                this.toServer.println(text);
            }while(!text.equalsIgnoreCase("bye"));
        }
    }
}
