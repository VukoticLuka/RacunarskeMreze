package Rokovi.Jan1_2021.Zadatak2;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Server{
    private static final int port = 31415;
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(port)){

            while (true){
                try {
                    byte[] buf = new byte[256];
                    DatagramPacket request = new DatagramPacket(buf, buf.length);
                    socket.receive(request);

                    String received_data = new String(request.getData(),0,request.getLength(), StandardCharsets.UTF_8);
                    double radius = Double.parseDouble(received_data);

                    double surface_area = radius * radius * Math.PI;

                    byte[] response_data = Double.toString(surface_area).getBytes(StandardCharsets.UTF_8);

                    DatagramPacket response = new DatagramPacket(response_data, response_data.length,
                            request.getAddress(), request.getPort());

                    socket.send(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


}
