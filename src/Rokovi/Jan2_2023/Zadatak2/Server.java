package Rokovi.Jan2_2023.Zadatak2;

import Rokovi.Jun1_2022.Zadatak2.ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {
    private static final int PORT = 1996;
    private static int lastId = 1;

    private static Map<Integer,ChessPlayer> players = new HashMap<>();

    private static final ExecutorService pool = Executors.newFixedThreadPool(5);
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PORT)){
            System.err.println("Listening for clients");
            while(true){
                Socket client = server.accept();
                System.err.println("Client accepted");
                pool.execute(new ClientHandler(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }

    public static int getPORT() {
        return PORT;
    }

    private static class ClientHandler implements Runnable{

        private final Socket client;

        ClientHandler(Socket client){
            this.client = client;
        }
        @Override
        public void run() {
            try(BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(),true))
            {
                String userInput;
                while((userInput = in.readLine()) != null){
                    System.out.println(userInput);
                    if(userInput.equalsIgnoreCase("bye"))
                        break;
                    String[] tokens = userInput.split(" ");
                    out.println(tokens[0]);

                    //debug purpose

                    if(tokens[0].equalsIgnoreCase("sel")){
                        if (tokens.length < 2){
                            out.println("Invalid input. Usage: sel id");
                        }
                        else if(players.containsKey(Integer.parseInt(tokens[1]))){
                            synchronized (players) {
                                out.println(players.get(Integer.parseInt(tokens[1])));
                            }
                        }
                        else{
                            out.println("Player with that id does not exist");
                        }
                    }
                    else if(tokens[0].equalsIgnoreCase("ins")){
                        if(tokens.length < 2){
                            out.println("Invalid input: Usage: ins <player name>");
                        }
                        else {
                            StringBuilder sb = new StringBuilder();
                            for(int i = 1; i < tokens.length; i++){
                                sb.append(tokens[i]);
                                sb.append(" ");
                            }
                            synchronized (players){
                                players.put(lastId++, new ChessPlayer(sb.toString(), 1300));
                            }
                            out.println("ins successfully executed");
                        }
                    } else if (tokens[0].equalsIgnoreCase("upd")) {
                        if(tokens.length < 3){
                            out.println("Invalid input. Usage: upd id elo");
                        }
                        else if(players.containsKey(Integer.parseInt(tokens[1]))){
                            int id = Integer.parseInt(tokens[1]);
                            int newElo = Integer.parseInt(tokens[2]);
                            synchronized (players){
                                players.get(id).setElo(newElo);
                            }
                            out.println("elo successfully executed");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


