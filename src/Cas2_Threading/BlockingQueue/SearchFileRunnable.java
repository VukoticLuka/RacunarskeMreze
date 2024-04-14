package Cas2_Threading.BlockingQueue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class SearchFileRunnable implements Runnable{

    private final BlockingQueue<Path> queue;
    private final String keyword;
    public SearchFileRunnable(BlockingQueue<Path> fileQueue, String keyword) {
        this.queue = fileQueue;
        this.keyword = keyword;
    }

    @Override
    public void run() {
        //koristimo take jer je blokirajuci
        try {
            boolean done = false;
            while(!done) {
                Path p = this.queue.take();
                if(p == FileTreeWorkerRunnable.END_OF_WORK){
                    done = true;
                    this.queue.put(p);
                }
                else {
                    this.search(p);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void search(Path f){

        try(Scanner sc = new Scanner(f)){
            for(int ln = 1; sc.hasNextLine(); ln++){
                String line = sc.nextLine();
                if(line.contains(this.keyword)){
                    System.out.printf("%s:%d\n", f.getFileName(),ln);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
