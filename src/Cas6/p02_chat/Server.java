package Cas6.p02_chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Server {
    private final static int PORT_NUM = 12345;


    public static void main(String[] args) {
        Server server = new Server(PORT_NUM);
        server.execute();
    }

    public static int getPortNum() {
        return PORT_NUM;
    }

    private final int port;
    private final Set<UserThread> users;

    public Server(int portNum) {
        this.port = portNum;
        this.users = Collections.synchronizedSet(new HashSet<>());
    }

    private void execute(){
        try(ServerSocket server = new ServerSocket(PORT_NUM)){
            System.err.println("Chat server is listening on port: " + this.port);

            while(true){
                Socket client = server.accept();
                System.err.println("Client connected!");

                // We dispatch a new thread for each user in the chat
                UserThread user = new UserThread(client,this);
                this.users.add(user);
                user.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getUsers(){
        synchronized (this.users){
            return this.users.stream()
                    .map(UserThread::getNickName)
                    .collect(Collectors.toList());
        }
    }

    public void brodacst(UserThread userThread, String s) {

        synchronized (this.users){
            this.users.stream()
                    .filter(u -> u != userThread)
                    .forEach(u -> u.sendMessage(s));
        }
    }
}
