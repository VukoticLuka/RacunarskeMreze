package Cas2_Threading.BankExample;

import java.util.concurrent.ThreadLocalRandom;

public class TransferRunnable implements Runnable{

    private static final int MAX_TRANSFER_DELAY = 1;

    private IBank bank;
    private int from;
    private int max;

    TransferRunnable(IBank bank, int from, int max){
        this.bank = bank;
        this.from = from;
        this.max = max;
    }
    @Override
    public void run() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        try {
            while (true){
                int to = r.nextInt(this.bank.count());
                int amount = r.nextInt(this.max);
                this.bank.transfer(this.from,to,amount);
                Thread.sleep(r.nextLong(MAX_TRANSFER_DELAY));
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
