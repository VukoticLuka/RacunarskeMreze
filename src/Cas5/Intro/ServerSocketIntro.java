package Cas5.Intro;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerSocketIntro {
    public static void main(String[] args) {

        int port = 9000;

        //ServerSocket is autocloseable

        try(ServerSocket ss = new ServerSocket(port)) {
            //Listens for a connection to be made to this socket and accepts it.
            // The method blocks until a connection is made
            Socket client = ss.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //we have another way of instancing SErverSocket
        //we can bind the port to our Socket

        try(ServerSocket ss2 = new ServerSocket()){
            ss2.bind(new InetSocketAddress(port));

            //usually servers are infinite loops
            while(true){
                Socket client = ss2.accept();

                serve(client);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void serve(Socket client){
        //serve client, usually in another thread
    }
}
