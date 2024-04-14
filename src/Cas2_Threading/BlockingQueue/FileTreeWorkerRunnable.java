package Cas2_Threading.BlockingQueue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

final public class FileTreeWorkerRunnable implements Runnable{
    static final Path END_OF_WORK = Paths.get("");
    private final BlockingQueue<Path> queue;
    private final Path startingDir;
    public FileTreeWorkerRunnable(Path path, BlockingQueue<Path> fileQueue) {
        this.queue = fileQueue;
        this.startingDir = path;
    }

    @Override
    public void run() {
        try {
            this.walk(startingDir);
            this.queue.put(END_OF_WORK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void walk(Path startingDir) throws InterruptedException {
        //imamo klasu DirectoryStream koja je autocloseable
        try (var ds = Files.newDirectoryStream(startingDir)){
            for(Path p : ds){
                if(Files.isDirectory(p)){
                    walk(p);
                }
                else {
                    //ako nema mesta u redu cekamo da se oslobodi mesto
                    this.queue.put(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
