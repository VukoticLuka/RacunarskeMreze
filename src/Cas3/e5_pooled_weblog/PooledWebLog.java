package Cas3.e5_pooled_weblog;

import java.io.*;
import java.util.*;

public class PooledWebLog implements AutoCloseable{
    private final BufferedReader in;
    private final BufferedWriter out;

    private final int threads;

    private boolean finished;

    private final List<Thread> workers;


    private final List<String> entries = Collections.synchronizedList(new LinkedList<>());
    PooledWebLog(InputStream in, PrintStream out, int threads){
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new BufferedWriter(new OutputStreamWriter(out));
        this.threads = threads;
        this.workers = new ArrayList<>(this.threads);
    }


    public void processLogFile() {

        //start the lookup threads
        for(int i = 0; i < this.threads; i++){
            Thread worker = new Thread();
            worker.start();
            this.workers.add(worker);
        }

        try {
            for(String entry = in.readLine(); entry != null; entry = in.readLine()){
                while (this.entries.size() > this.threads){
                    try {
                        Thread.sleep(1000/this.threads);
                    } catch (InterruptedException e) {
                        //throw new RuntimeException(e);
                        this.finished = true;
                        System.err.println("Work interrupted! Signalling...");
                        synchronized (this.entries){
                            this.entries.notifyAll();
                        }
                    }

                }
                synchronized (this.entries){
                    this.entries.add(0,entry);

                    this.entries.notifyAll();
                }
                Thread.yield();
            }
            //Signalize threads that the work is finished
            System.err.println("Work finished!");
            synchronized (this.entries){
                this.entries.notifyAll();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getEntries() {
        return entries;
    }

    public void log(String entry) throws IOException {
        this.out.write(entry);
        this.out.newLine();

        this.out.flush();
    }

    boolean isFinished(){
        return this.finished;
    }
    @Override
    public void close() throws Exception {
        this.in.close();

        // We have to wait for workers to finish in order to close the output stream (they use it for logging)
        for(Thread worker : workers){
            worker.join();
        }
        this.out.close();
    }
}
