package Cas3.WebLog_2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        WebLog wl = new WebLog(new FileInputStream("src/Cas3/WebLog_2/apache.txt")
                            , System.out, 6);

        wl.processLogFile();
        wl.close();
    }
}
