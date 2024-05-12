package Rokovi.Jan1_2021.Zadatak2;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private static final int port = 31415;
    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket();
            Scanner sc = new Scanner(System.in))
        {
            socket.setSoTimeout(5000);
            String msg = sc.nextLine().trim();

            byte[] buf = msg.getBytes(StandardCharsets.UTF_8);

            InetAddress address = InetAddress.getLocalHost();

            DatagramPacket request = new DatagramPacket(buf, buf.length,
                    address,port);

            socket.send(request);

            byte[] response_data = new byte[256];

            DatagramPacket response = new DatagramPacket(response_data, response_data.length);

            socket.receive(response);

            String surface_area = new String(response_data, 0, response.getLength(), StandardCharsets.UTF_8);

            System.out.println("Server response: " + surface_area);

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
