package Cas2_Threading.BankExample;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedBank implements IBank{
    private final int[] accounts;

    SynchronizedBank(int accountsNum, int initialBalance){
        accounts = new int[accountsNum];
        Arrays.fill(accounts,initialBalance);

    }
    @Override
    public synchronized void transfer(int from, int to, int amount){

        //dok nemam novca cekam
        while (this.accounts[from] < amount) {
            try {
                this.wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        accounts[to] += amount;

        //check to se if git works

        System.out.printf("Tranfer from %2d to %2d: %4d\n", from, to, amount);

        System.out.println("Total balance: " + this.getTotalBalance());

        //we also use the Objects's notifyAll() method
        //to signal the threads that the transfer has been made
        this.notifyAll();


        //ako nam ne treba cela funckija da bude synchronized
        //mozemo da smestimo u block
        //synchronized(this){
        //  //to do logic that needs to be synchronized
        // }
    }

    @Override
    public int count() {
        return this.accounts.length;
    }

    @Override
    public int getTotalBalance() {
        return Arrays.stream(this.accounts).sum();
    }
}
