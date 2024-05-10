package Cas6.p02_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserThread extends Thread{

    private final Server server;
    private final Socket socket;

    private BufferedReader fromUser;

    private PrintWriter toUser;

    private String name;


    public UserThread(Socket socket,Server server){
        this.socket = socket;
        this.server = server;

        try {
            this.fromUser = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.toUser = new PrintWriter(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //Upon connecting, read username and send connected user list
        try {
            this.name = this.fromUser.readLine();
            this.sendMessage("Connected users: " + this.server.getUsers());

            this.server.brodacst(this, "New user connected: " + this.name);

            String clientMessage;
            do{
                clientMessage = fromUser.readLine();
                if(clientMessage == null)
                    break;

                this.server.brodacst(this,"[" + this.name + "]: " + clientMessage);
            }while (!clientMessage.equalsIgnoreCase("bye"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String msg){
        if(this.toUser != null)
            this.toUser.println(msg);
    }


    public String getNickName() {
        return this.name;
    }
}
