package Cas2_Threading.BankExample;

import java.util.Arrays;

public class DefaultBank implements IBank{

    private final int[] accounts;

    DefaultBank(int accountsNum, int initialBalance){
        accounts = new int[accountsNum];
        Arrays.fill(accounts,initialBalance);
    }
    @Override
    public void transfer(int from, int to, int amount) {
        if(this.accounts[from] < amount){
            return;
        }

        System.out.println(Thread.currentThread());
        accounts[from] -= amount;
        accounts[to] += amount;

        System.out.printf("Tranfer from %2d to %2d: %4d\n", from,to,amount);

        System.out.println("Total balance: " + this.getTotalBalance());

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
