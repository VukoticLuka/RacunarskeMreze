package Rokovi.Jun1_2022.Zadatak2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ClientHandler implements Runnable{

    private final Socket client;
    public ClientHandler(Socket client){
        this.client = client;
    }
    @Override
    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true))
        {
            Random rand = new Random();
            int number = rand.nextInt(100) + 1;

            int client_answer;
            out.println("Pogodi broj od 1 do 100 koji sam zamislio");
            do{
                client_answer = Integer.parseInt(in.readLine().trim());

                if(client_answer > number){
                    out.println("Zamisljeni broj je manji od toga!");
                }
                else if(client_answer < number){
                    out.println("Zamisljeni broj je veci od toga!");
                }else{
                    out.println("Cestitam pogodili ste broj!");
                }

            }while(client_answer != number);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
