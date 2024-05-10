package Rokovi.Jun1_2022.Zadatak2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Client client = new Client(Server.PORT,"localhost");
        client.execute();
    }

    private final int portnum;
    private final String host;

    public Client(int portnum, String host){
        this.host = host;
        this.portnum = portnum;
    }

    private void execute(){
        try(Socket socket = new Socket(this.host,portnum)){

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);

            System.out.println(in.readLine());

            String server_answer;

            do{
                out.println(sc.nextLine());
                server_answer = in.readLine();
                System.out.println(server_answer);
            }while(!server_answer.startsWith("Cestitam"));

            in.close();
            out.close();
            sc.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
