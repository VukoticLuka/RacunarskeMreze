package Rokovi.Jan2_2023.Zadatak2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", Server.getPORT())){
            System.err.println("Connected to server on port " + socket.getPort());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

            String msg;
            //String serverRespones;

            while ((msg = userInput.readLine()) != null) {
                out.println(msg);
                if (msg.equalsIgnoreCase("bye"))
                    break;
                System.out.println("Server response: " + in.readLine());
            }
            in.close();
            out.close();
            userInput.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
