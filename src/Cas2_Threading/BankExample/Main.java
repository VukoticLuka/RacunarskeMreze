package Cas2_Threading.BankExample;

import java.awt.datatransfer.Transferable;

public class Main {
    private static final int ACCOUNTS = 5;
    private static final int STARTING_BALANCE = 100000;

    public static void main(String[] args) {
        IBank bank = new DefaultBank(ACCOUNTS,STARTING_BALANCE);

        for(int i = 0; i < ACCOUNTS; i++){
            TransferRunnable transfer = new TransferRunnable(bank,i,10);
            Thread t = new Thread(transfer);
            t.start();
        }
    }
}
