package Cas1.Streams;

import java.io.*;

public class CopyText {
    public static void main(String[] args) {
        try{
            long startTime = System.currentTimeMillis();

            // Note that we do not need to use Readers here - we are just copying bytes and there is no
            // need for viewing the data on a "higher" level than bytes

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream("src/Cas1/Streams/in.txt")
                    )
            );


            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("src/Cas1/Streams/out.txt")
                    )
            );

            char[] buf = new char[512];

            int bytesRead = 0;

            while ((bytesRead = br.read(buf)) != -1){
                bw.write(buf,0,bytesRead);
            }

            long endTime = System.currentTimeMillis();

            System.out.println((endTime-startTime));

            br.close();
            bw.close();

            // close() flushes the stream as well - this is important in the buffered variant

            long start = System.currentTimeMillis();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream("src/Cas1/Streams/in.txt")
                    )
            );


            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("src/Cas1/Streams/out2.txt")
                    )
            );

            int b;
            //2 i po puta sporije od baferisane varijante
            while((b = in.read()) != -1){
                out.write(b);
            }

            long end = System.currentTimeMillis();

            System.out.println((end-start));

            in.close();
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
