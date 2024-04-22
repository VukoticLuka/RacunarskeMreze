package Cas6.PracticeChat;

import Cas6.p02_chat.Server;
import Cas6.p02_chat.UserThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatServer {
    private static final int PORT_NUM = 12345;

    public static void main(String[] args) {
        ChatServer server = new ChatServer(PORT_NUM);
        server.execute();
    }

    private int port;
    private Set<UserThread2> users;
    ChatServer(int port){
        this.port = port;
        this.users = Collections.synchronizedSet(new HashSet<>());
    }


    private void execute(){
        try(ServerSocket server = new ServerSocket(this.port)){

            while (true){
                Socket client = server.accept();
                System.err.println("Client accepted!");

                UserThread2 user = new UserThread2(client,this);
                this.users.add(user);
                user.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void remove(UserThread2 user){
        String username = user.getUsername();
        this.users.remove(user);
        System.err.println("Client disconeccted: " + username);
    }

    List<String> getUsers(){
        return this.users.stream()
                .map(UserThread2::getUsername)
                .collect(Collectors.toList());
    }


    public static int getPortNum() {
        return PORT_NUM;
    }

    public void brodcast(UserThread2 user, String s) {
        synchronized (this.users){
            this.users.stream()
                    .filter(u -> u != user)
                    .forEach(u -> u.sendMessage(s));
        }
    }
}
