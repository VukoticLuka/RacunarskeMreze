package Cas5.DayTimeService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {
    public final static int PORT_NUM = 8765;

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PORT_NUM)){
            System.err.println("Server bound port: " + PORT_NUM);

            while (true){
                System.err.println("Listening for clients...\n");

                //accept() is a blocking call
                try(Socket client = server.accept()){
                    System.err.println("Client accepted\n");

                    //We work with clients in a single thread
                    try(var out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))){
                        Date now = new Date();

                        out.write(now.toString());

                        //We must send a trailing newline since client calls `readLine()`
                        out.newLine();
                        out.flush();
                    }catch (IOException e){
                        //ignore broken connection
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
