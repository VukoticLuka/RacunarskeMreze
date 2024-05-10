package Rokovi.Jun1_2022.Zadatak2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 12321;
    public static void main(String[] args) {
        Server server = new Server(PORT);
        server.execute();
    }

    private final int portnum;

    public Server(int portnum){
        this.portnum = portnum;
    }

    private void execute(){
        try(ServerSocket server = new ServerSocket(this.portnum)){
            System.err.println("Listening for clients");

            while(true){
                Socket client = server.accept();

                System.err.println("Client accepted on port " + this.portnum);

                new Thread(new ClientHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
