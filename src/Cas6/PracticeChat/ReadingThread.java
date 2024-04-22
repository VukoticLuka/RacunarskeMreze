package Cas6.PracticeChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadingThread extends Thread{

    private final String name;
    private BufferedReader fromServer;
    public ReadingThread(String username,Socket socket) {
        this.name = username;
        try {
            fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true){
            try {
                String response;
                response = fromServer.readLine();

                if(response == null){
                    System.err.println("\rConnection lost!");
                    return;
                }

                System.out.println("\r" + response);

                System.out.printf("\r[%s]",this.name);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
