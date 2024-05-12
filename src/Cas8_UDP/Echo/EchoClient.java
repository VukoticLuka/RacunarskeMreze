package Cas8_UDP.Echo;

import java.io.Closeable;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EchoClient implements Closeable {
    private final InetAddress address;
    private final int port;
    private final DatagramSocket socket;
    public EchoClient(String localhost, int port) throws SocketException, UnknownHostException {
        this.socket = new DatagramSocket();
        this.socket.setSoTimeout(5000);
        this.address = InetAddress.getByName(localhost);
        this.port = port;
    }

    @Override
    public void close() throws IOException {
        this.socket.close();
    }

    public String sendEcho(String s) throws IOException {
        byte[] buf = s.getBytes(StandardCharsets.UTF_8);

        System.err.println("Client send msg: " + s + " " + Arrays.toString(buf));
        DatagramPacket request = new DatagramPacket(buf,buf.length,this.address,this.port);
        socket.send(request);

        DatagramPacket response = new DatagramPacket(buf, buf.length);
        System.err.println("Client recv: " + Arrays.toString(response.getData()));

        return new String(response.getData(), 0,response.getLength(), StandardCharsets.UTF_8);
    }
}
