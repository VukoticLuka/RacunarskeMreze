package Cas2_Threading.ExecutorsExample;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MatchCounter implements Callable<Integer> {
    private Path directory;
    private String keyword;
    private ExecutorService pool;

    MatchCounter(Path directory, String keyword, ExecutorService pool){
        this.directory = directory;
        this.keyword = keyword;
        this.pool = pool;
    }
    @Override
    public Integer call() throws Exception {
        int count = 0;
        try{
            ArrayList<Future<Integer>> results = new ArrayList<Future<Integer>>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(this.directory)){
                for(Path file : stream){
                    if(Files.isDirectory(file)){
                        MatchCounter counter = new MatchCounter(file,keyword,pool);
                        Future<Integer> result = pool.submit(counter);
                        results.add(result);
                    } else if (this.search(file)) {
                        count++;
                    }
                }
            }catch (IOException | DirectoryIteratorException x){
                System.err.println(x);
            }

            for(Future<Integer> result : results){
                try {
                    count += result.get();
                }catch (ExecutionException e){
                    e.printStackTrace();
                }
            }
        }catch (InterruptedException e){

        }
        return count;
    }

    public boolean search(Path file) {
        try(Scanner sc = new Scanner(file)){
            boolean found = false;
            while (!found && sc.hasNextLine()){
                String line =sc.nextLine();
                if(line.contains(this.keyword)){
                    found = true;
                }
            }
            sc.close();
            return found;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
