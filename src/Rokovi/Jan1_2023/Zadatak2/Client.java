package Rokovi.Jan1_2023.Zadatak2;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket(Server.HOST, Server.PORT_NUM);
            Scanner sc = new Scanner(System.in)){
            System.err.println("Client connected on port " + socket.getPort());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String line = in.readLine();
            System.out.println(line);

            while(true){
                String pokusaj = sc.next();
                out.println(pokusaj);

                String odgovor = in.readLine();
                System.out.println(odgovor);
                if(odgovor.startsWith("Nisi uspeo da pogodis") || odgovor.startsWith("Cestitam"))
                    break;
            }

            in.close();
            out.close();

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
