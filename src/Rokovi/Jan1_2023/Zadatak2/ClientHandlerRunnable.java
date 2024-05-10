package Rokovi.Jan1_2023.Zadatak2;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClientHandlerRunnable implements Runnable{

    private Socket client;
    //private String word;
    public ClientHandlerRunnable(Socket client){
        this.client = client;
    }
    @Override
    public void run() {
        try(BufferedReader filereader = new BufferedReader(new InputStreamReader(
                new FileInputStream("src/Rokovi/Jan1_2023/Zadatak2/moguce_reci.txt")));
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(), true))
        {
            List<String> words = new ArrayList<>();
            String line;
            while((line = filereader.readLine()) != null){
                words.add(line.trim());
            }

            Random rand = new Random();

            int num = rand.nextInt(words.size());

            String chosenWord = words.get(num);

            out.println("Pogodi o cemu razmisljam... " + words.toString());

            for(int i = 0; i < 3; i++){
                String odgovor = in.readLine();
                if(odgovor.equalsIgnoreCase(chosenWord)){
                    out.println("Cestitam pogodili ste rec...");
                    break;
                }
                else{
                    if(i == 2){
                        out.println("Nisi uspeo da pogodis... " + chosenWord);
                    }
                    else{
                        out.println("Netacno! (" + chosenWord.substring(0,i+1) + ")");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
