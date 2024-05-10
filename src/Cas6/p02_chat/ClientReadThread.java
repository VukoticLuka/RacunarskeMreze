package Cas6.p02_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReadThread extends Thread{

    //private final Socket server;
    private BufferedReader fromServer;
    private String  username;

    public ClientReadThread(String username,Socket server){
        this.username = username;
        try {
            this.fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while(true){
            try{
                //wait for server
                String response = fromServer.readLine();

                if(response == null){
                    System.err.println("\rConnection lost");
                    return;
                }
                System.out.println("\r" + response);

                //Print prompt
                System.out.printf("\r[%s]", this.username);

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
