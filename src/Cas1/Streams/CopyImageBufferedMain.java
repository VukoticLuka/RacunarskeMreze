package Cas1.Streams;

import java.io.*;

public class CopyImageBufferedMain {
    public static void main(String[] args) {


        {
            try {
                long timeStart = System.currentTimeMillis();
                BufferedInputStream bsin = new BufferedInputStream(
                        new FileInputStream("src/Cas1/Streams/in.PNG")
                );

                BufferedOutputStream bsout = new BufferedOutputStream(
                        new FileOutputStream("out.PNG")
                );

                byte[] buf = new byte[512];
                int bytesRead = 0;

                while ((bytesRead = bsin.read(buf)) != -1) {
                    bsout.write(buf, 0, bytesRead);
                }

                bsout.close();
                bsin.close();


            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
