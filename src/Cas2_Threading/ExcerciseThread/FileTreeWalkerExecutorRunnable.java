package Cas2_Threading.ExcerciseThread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

public class FileTreeWalkerExecutorRunnable implements Runnable{
    private final Path path;
    private final String keyword;

    private final ExecutorService executor;
    public FileTreeWalkerExecutorRunnable(Path path, String keyword, ExecutorService executor) {
        this.path = path;
        this.keyword = keyword;
        this.executor = executor;
    }

    @Override
    public void run() {
        // Files.walk(Path) will walk through all the files for us
        try(Stream<Path> filesStream = Files.walk(this.path)){
            filesStream.filter(Files::isRegularFile).forEach(path1 -> executor.submit(makeSearchFileRunnable(path,keyword)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static Runnable makeSearchFileRunnable(Path path, String keyword){
        return () -> {
            // this is equivalent to the logic in SearchFileRunnable, which used Scanner to read file line-by-line
            // if we didn't care about reading huge files all at once, we could've used Files.readLines(path) and iterated over lines
            // if we didn't care about reading huge files and line numbers, we could've used just Files.readString(path).contains(keyword);
            try(Stream<String> linesStream = Files.lines(path)) {
                int[] keywordPositions = linesStream.mapToInt(line -> line.contains(keyword) ? 1 : 0).toArray();
                for(int i=0; i<keywordPositions.length; i++) {
                    if(keywordPositions[i] == 1) {
                        System.out.printf("%s:%d\n", path.getFileName(), i);
                    }
                }
            } catch (IOException e) {
                System.err.printf("Failed to process path %s. Unexpected %s occurred: %s\n", path, e.getClass().getSimpleName(), e.getMessage());
            }
        };
    }
}
