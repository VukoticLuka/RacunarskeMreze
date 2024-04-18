package Cas3.WebLog_2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class WebLog implements AutoCloseable{

    private final BufferedReader in;
    private final BufferedWriter out;

    private final int num_of_threads;

    private boolean finished;

    private final List<Thread> workers;

    private final List<String> entries = Collections.synchronizedList(new LinkedList<>());
    WebLog(InputStream in, PrintStream out, int num_of_threads){
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new BufferedWriter(new OutputStreamWriter(out));
        this.num_of_threads = num_of_threads;
        this.workers = new ArrayList<>(this.num_of_threads);
    }
    public void processLogFile() {
        //Starting the loop of threads
        for(int i = 0; i < this.num_of_threads; i++){
            Thread worker = new Thread(new LookupRunnable(this));
            this.workers.add(worker);
        }

        //reading logfile line by line

        try {
            for(String entry = in.readLine(); entry != null; entry = in.readLine()){
                // Since log file is very large and because threads will need more time
                // to do their logic than it takes to read a line, the list will grow
                // in size. We do not want for our list to too become large so we wait
                // a bit if the list size is greater than threads amount so threads
                // can catch up
                while (this.entries.size() > this.num_of_threads){
                    try {
                        Thread.sleep(1000/this.num_of_threads);
                    } catch (InterruptedException e) {
                        //throw new RuntimeException(e);
                        this.finished = true;
                        System.err.println("Work interrupted!");
                        synchronized (this.entries){
                            this.entries.notifyAll();
                        }
                    }
                }

                synchronized (this.entries){
                    this.entries.add(entry);

                    this.entries.notifyAll();
                }

                Thread.yield();
            }

            this.finished = true;
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

    public boolean isFinished() {
        return finished;
    }

    public void log(String entry) throws IOException {
        //da mi upise u fajl koji treba
        this.out.write(entry);
        this.out.newLine();
        this.out.flush();
    }

    @Override
    public void close() {
        try {
            this.in.close();

            for(Thread worker : workers){
                worker.join();
            }

            this.out.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
