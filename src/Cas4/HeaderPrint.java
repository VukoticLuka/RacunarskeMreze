package Cas4;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class HeaderPrint {
    public static void main(String[] args) {
        //mi zelimo da izvucemo header od svakog url-a
        //kao response

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()){
            try{
                String line = sc.nextLine();
                if(line.trim().equals("")){
                    continue;
                }
                if(line.equalsIgnoreCase("exit")){
                    break;
                }

                URL u = new URL(line);
                URLConnection conn = u.openConnection();

                System.out.println(conn.getHeaderField(0));

                for(int i = 0; ;i++){
                    String header = conn.getHeaderField(i);
                    if(header == null)
                        break;
                    System.out.println(conn.getHeaderFieldKey(i) + ":" + header);
                }

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
