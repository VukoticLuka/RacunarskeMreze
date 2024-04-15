package Cas2_Threading.ExcerciseThread;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class ThreadPoolBlockingQueueMain {
    static final int QUEUE_SIZE = 10;
    static final int THREADS_NUM = 5;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter root directory: ");
        String dir = in.nextLine();
        System.out.println("Enter keyword: ");
        String keyword = in.nextLine();

        in.close();

        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(QUEUE_SIZE);

        ExecutorService executor = new ThreadPoolExecutor(THREADS_NUM,THREADS_NUM,0,
                TimeUnit.SECONDS,
                workQueue,new ThreadPoolExecutor.CallerRunsPolicy());

        Future<?> fileTreeWalker = executor.submit(new FileTreeWalkerExecutorRunnable(Paths.get(dir),keyword,executor));

        //get(long, TimeUnit) will wait for Future to finish, or throw an exception if it times out
        try {
            fileTreeWalker.get(30, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            fileTreeWalker.cancel(true);
        }

        executor.shutdown();
//
//        Kada pozovete ExecutorService.awaitTermination(timeout, unit), nit koja je pozvala ovu metodu će čekati da se svi zadaci izvrše ili dok ne prođe određeno vreme, koje je određeno timeout parametrom, izraženim u jedinicama koje su definisane parametrom unit.
//
//                Ako su svi zadaci izvršeni pre isteka vremena, metoda će se vratiti true.
//                Ako istekne vreme pre nego što su svi zadaci završeni, metoda će se vratiti false.

        try {
            if(!executor.awaitTermination(30,TimeUnit.SECONDS)){
                System.err.println("Executor seems to be still working after 30s of waiting. Shutting down forcefully...");
                List<Runnable> runnables = executor.shutdownNow();
                System.err.printf("There were %d unfinished tasks.\n", runnables.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Program finished");
    }
}
