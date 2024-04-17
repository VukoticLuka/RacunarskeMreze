package Cas3.e5_pooled_weblog;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class LookUpRunnable implements Runnable{

    private final PooledWebLog log;

    private final List<String> entries;

    LookUpRunnable(PooledWebLog pw){
        this.log = pw;
        this.entries = pw.getEntries();
    }
    @Override
    public void run() {
        for(String entry = this.getNextEntry(); entry != null; entry = this.getNextEntry()){
            String workResult = this.analyzeEntryAndGetResult(entry);

            if(workResult == null){
                continue;
            }

            try {
                this.log.log(workResult);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Thread.yield();
        }
    }

    private String getNextEntry(){
        synchronized (this.entries){
            while (this.entries.size() == 0){
                if(this.log.isFinished()){
                    System.err.println("Thread exiting " + Thread.currentThread());
                    return null;
                }

                //if the log is not precessed fully, we wait for a line
                try {
                    this.entries.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return entries.remove(0);
        }
    }

    private String analyzeEntryAndGetResult(String entry){
        int index = entry.indexOf(' ');
        if(index == -1)
            return null;

        String remoteHost = entry.substring(0,index);

        //Look up host Ip address to find hostname
        try {
            remoteHost = InetAddress.getByName(remoteHost).getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        return remoteHost;
    }
}
