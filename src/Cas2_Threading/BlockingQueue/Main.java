package Cas2_Threading.BlockingQueue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

final public class Main {
    private static final int FILE_QUEUE_SIZE = 10;
    private static final int THREAD_NUM = 5;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter base dir: ");
        String dir = sc.nextLine();

        System.out.println("Enter key word: ");
        String keyword = sc.nextLine();

        sc.close();


        //koristicemo konkurentnu strukturu podataka
        BlockingQueue<Path> fileQueue = new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

        //i mi za ovaj interfejs imamo blokirajuce i neblokirajuce metode
        //nama u ovom slucaju trebaju blokirajuce kao sto su put ili take..


        //Start directory traversal
        //var je kao auto u c++

        var ftw = new FileTreeWorkerRunnable(Paths.get(dir), fileQueue);

        new Thread((ftw)).start();


        //Start workers

        for(int i = 0; i < THREAD_NUM; i++){
            new Thread(new SearchFileRunnable(fileQueue,keyword)).start();
        }

    }
}
