package Cas3.e5_pooled_weblog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PooledWeblogTest {
    public static void main(String[] args) {

        try {
            PooledWebLog pwl = new PooledWebLog(new FileInputStream("src/Cas3/e5_pooled_weblog/apache.logfile")
                , System.out, 6);

            pwl.processLogFile();

            pwl.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
