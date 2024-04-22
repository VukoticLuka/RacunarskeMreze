package Cas6.PracticeChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserThread2 extends Thread{

    private final Socket socket;

    private final ChatServer server;

    private BufferedReader fromUser;
    private PrintWriter toUser;

    private String username;


    public UserThread2(Socket socket,ChatServer server){
        this.socket = socket;
        this.server = server;

        try {
            fromUser = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            toUser = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.username = this.fromUser.readLine();
            this.sendMessage("Connected users: " + this.server);

            this.server.brodcast(this,"New user connected: " + this.username);

            String clientMessage;

            do{
                clientMessage = this.fromUser.readLine();
                if(clientMessage == null)
                    break;
                this.server.brodcast(this,"[" + this.username +  "]: "+  clientMessage);
            }while (!clientMessage.equalsIgnoreCase("bye"));

            this.server.brodcast(this, this.username + " has left the chat!");

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            this.server.remove(this);

            //close socket
            try {
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    void sendMessage(String msg){
        if(this.toUser != null){
            this.toUser.println(msg);
        }
    }


    public String getUsername() {
        return username;
    }
}
