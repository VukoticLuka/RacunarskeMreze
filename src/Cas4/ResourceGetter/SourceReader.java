package Cas4.ResourceGetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SourceReader {
    private static final String URL_STRING = "http://poincare.matf.bg.ac.rs/~marko.spasic/";
    public static void main(String[] args) throws IOException {

        URL url = new URL(URL_STRING);
        //I nacin

        try(BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        url.openStream())
        ))
        {
            //ispisacemo u sys.out
            String line;
            while ((line = in.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("=============================");
        //II nacin sa otvaranjem konekcije

        URLConnection conn = url.openConnection();
        String encoding = conn.getContentEncoding();

        if(encoding == null){
            encoding = "UTF-8";
        }

        try(BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(),encoding)
        )){
            String line;
            while((line = in.readLine()) != null){
                System.out.println(line);
            }
        }
        catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
    }
}
