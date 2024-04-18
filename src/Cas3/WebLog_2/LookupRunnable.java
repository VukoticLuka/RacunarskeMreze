package Cas3.WebLog_2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class LookupRunnable implements Runnable{

    private final WebLog log;
    private final List<String> entries;
    LookupRunnable(WebLog wl){
        this.log = wl;
        entries = wl.getEntries();
    }
    @Override
    public void run() {
            for (String entry = this.getNextEntry(); entry != null; entry = this.getNextEntry()) {
                String workResult = this.analyzeEntryAndGetResult(entry);

                if (workResult == null) {
                    continue;
                }
                try {
                    this.log.log(workResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Thread.yield();
            }
    }


    private String getNextEntry(){
        synchronized (this.entries) {
            while (this.entries.size() == 0) {
                if (this.log.isFinished()) {
                    System.err.println("Thread exiting: " + Thread.currentThread());
                    return null;
                }

                try {
                    this.entries.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return this.entries.remove(0);
        }
    }

    private String analyzeEntryAndGetResult(String entry){
        //analiziramo entry
        int index = entry.indexOf(' ');
        if(index == -1)
            return null;

        String remoteHost = entry.substring(0,index);

        try {
            remoteHost = InetAddress.getByName(remoteHost).getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return remoteHost;
    }
}
