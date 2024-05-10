package Cas6.p02_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        Client client = new Client("localhost", Server.getPortNum());
        System.err.println("Connecting to local port: " + Server.getPortNum());
        client.execute();
    }


    private final String hostname;
    private final int portnum;
    private String name;
    Client(String hostname, int portnum){
        this.hostname = hostname;
        this.portnum = portnum;
    }


    private void execute() {
        try{
            this.setName();

            try(Socket socket = new Socket(this.hostname,this.portnum)){
                System.err.println("Connecting to the chat server @ " + this.hostname);

                var rt = new ClientReadThread(this.name,socket);
                rt.start();

                var wt = new ClientWriteThread(this.name,socket);

                wt.start();

                rt.join();
                wt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setName() throws IOException {
        System.out.println("Enter your name: ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        this.name = in.readLine();
        //we can not close in since we will use it later
    }
}
