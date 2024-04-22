package Cas6.PracticeChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WritingThread extends Thread{

    private String username;
    //private Socket socket;

    private PrintWriter toServer;
    public WritingThread(String username,Socket socket) {
        //this.socket = socket;
        this.username = username;
        try {
            toServer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        //we need to read from stdin and to write to server
        this.toServer.println(this.username);

        try(Scanner sc = new Scanner(System.in)){
            String text;
            do{
                System.out.printf("\r[%s]: ", this.username);
                text = sc.nextLine();
                this.toServer.println(text);
            }while(!text.equalsIgnoreCase("bye"));
        }
    }
}
