package Cas4.ResourceGetter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class BinaryFileDownloader {
    public static void main(String[] args) {
        try {
            URL u = new URL("http://www.matf.bg.ac.rs/images/matf.gif");
            URLConnection conn = u.openConnection();
            String contentType = conn.getContentType();
            int fileLength = conn.getContentLength();

            if(fileLength == -1 || contentType.startsWith("text")){
                throw new IOException("Content is not binary file");
            }

            //koristimo BufferInput stream jer nije tekstualni fajl

            BufferedInputStream in = new BufferedInputStream(conn.getInputStream());

            String filename = u.getFile();
            System.out.println(filename);
            filename = filename.substring(filename.lastIndexOf('/')+1);

            try (BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream("src/Cas4/ResourceGetter/matf.gif")
            );
            ){
                for(int i = 0; i < fileLength; i++){
                    int b = in.read();
                    out.write(b);
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
