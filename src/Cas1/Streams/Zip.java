package Cas1.Streams;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class Zip {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();

        BufferedInputStream in = new BufferedInputStream(
                new FileInputStream("src/Cas1/Streams/in.txt")
        );

        BufferedOutputStream out = new BufferedOutputStream(
                new GZIPOutputStream(
                        new FileOutputStream("src/Cas1/Streams/out.gz")
                )
        );

        byte[] buf = new byte[512];
        int readBytes = 0;

        while((readBytes = in.read()) != -1){
            out.write(buf,0,readBytes);
        }

        in.close();
        out.close();

        long stop = System.currentTimeMillis();

        System.out.println(stop-startTime);

    }
}
