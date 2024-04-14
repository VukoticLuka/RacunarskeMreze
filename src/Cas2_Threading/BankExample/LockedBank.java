package Cas2_Threading.BankExample;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockedBank implements IBank{
        private final int[] accounts;
        private final Lock lock;
        private final Condition condition;
        LockedBank(int accountsNum, int initialBalance){
            accounts = new int[accountsNum];
            Arrays.fill(accounts,initialBalance);
            this.lock = new ReentrantLock();
            this.condition = this.lock.newCondition();
        }
        @Override
        public void transfer(int from, int to, int amount){
            this.lock.lock();
            int balance_sum = 0;
            try{
                //dok nemam novca cekam
                while(this.accounts[from] < amount){
                    try {
                        this.condition.await();
                        //ovo cekanje atomicki otpusta lock
                        //tako da ne blokiram druge niti
                    }
                    catch (InterruptedException e){
                        new RuntimeException(e);
                    }
                }

                System.out.println(Thread.currentThread());
                accounts[from] -= amount;
                accounts[to] += amount;
                balance_sum = this.getTotalBalance();

                //ovde treba da ubacimo signal ako smo nekome ko nije imao dovoljno para
                //poslali da on moze da izvrsi transakciju na koju ceka
                //da bi poslali signal moramo da drzimo lock
                this.condition.signalAll();
                //ovo ce da posalje signal svima koji cekaju

            } finally {
                this.lock.unlock();
            }

            System.out.printf("Tranfer from %2d to %2d: %4d\n", from,to,amount);

            System.out.println("Total balance: " + balance_sum);
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
