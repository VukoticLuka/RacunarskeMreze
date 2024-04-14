package Cas2_Threading.ExecutorsExample;

import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolExample {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter base directory: ");

        String dir = in.nextLine();

        System.out.println("Enter keyword: ");
        String keyword = in.nextLine();

        in.close();

        ExecutorService pool = Executors.newCachedThreadPool();

        MatchCounter mc = new MatchCounter(Paths.get(dir),keyword,pool);

        Future<Integer> result = pool.submit(mc);

        try{
            System.out.println(result.get() + " matching files");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }
}
