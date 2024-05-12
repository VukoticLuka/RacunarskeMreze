package Cas8_UDP.Echo;

import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoServer extends Thread implements Closeable {

    private final DatagramSocket socket;
    public EchoServer(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
    }


    @Override
    public void run() {
        System.err.println("Server started...");
        try{
            while(true){
                byte[] buf = new byte[256];
                try{
                    //Wait for packages
                    DatagramPacket request = new DatagramPacket(buf, buf.length);
                    this.socket.receive(request);

                    //Send response to client
                    DatagramPacket response = new DatagramPacket(buf,0,request.getLength(),
                            request.getAddress(), request.getPort());
                    this.socket.send(response);

                    //CHeck if end is received
                    String received = new String(buf,0,request.getLength(), StandardCharsets.UTF_8);
                    System.err.println("Server received: " + received);
                    if(received.equalsIgnoreCase("end")){
                        System.err.println("Server received end signal");
                        break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            System.err.println("Server is shutting down...");
            this.close();
            System.err.println("Server successfully closed!");
        }

    }

    @Override
    public void close(){
        this.socket.close();
    }
}
