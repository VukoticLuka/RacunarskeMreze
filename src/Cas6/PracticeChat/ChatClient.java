package Cas6.PracticeChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", ChatServer.getPortNum());
        client.execute();
    }


    private final String hostname;
    private final int port;
    private String name;


    public ChatClient(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }


    private void execute(){
        try {
            this.setName();

            try(Socket socket = new Socket(this.hostname,this.port)){
                System.err.println("Connected to a server @ " + this.hostname);

                var rt = new ReadingThread(this.name,socket);

                rt.start();

                var wt = new WritingThread(this.name,socket);

                wt.start();

                rt.join();

                wt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setName() throws IOException {
        System.out.println("Enter your name: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        this.name = in.readLine();
    }
}
