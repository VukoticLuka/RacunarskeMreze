package Cas1.Streams;

import java.io.*;
import java.util.Scanner;

public class SelektivnoKopiranje {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        File file = new File(sc.next());

        if(file.exists()){
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath())));

                BufferedWriter bw  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt")));

                char[] text = new char[512];
                String line;
                while((line = br.readLine()) != null){
                    bw.write(line);
                }

                br.close();
                bw.close();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {

            }
        }
    }
}
