package Cas2_Threading.Intro;

import java.util.concurrent.ThreadLocalRandom;

public class RaceCondition {
    private static int x = 0;
    private static final int LIMIT = 100000000;

    public static class Test implements Runnable{
        @Override
        public void run() {
            for(int i = 0; i < LIMIT; i++){
                x++;
            }
        }
    }
    public static void main(String[] args) throws InterruptedException{
        int TH_NUM = 10;
        //klasican primer trke za resursima
        for(int j = 0; j < TH_NUM; j++){
            new Thread(new Test()).start();
        }
        Thread.sleep(5000);

        System.out.println("Expected: " + LIMIT*TH_NUM);
        System.out.println("Actual  : " + x);

        //problem je u tome sto x++ nije atomicna operacija
        //i desava se da u jednoj niti kada hocemo da dodamo ++1
        // druga nit prekine za x = 10 i kod sebe promeni na x = 11
        // ali kada se vrati izvrsavanje prethodnoj niti kod nje je jos uvek 10
        // i onda se tek menja na 11
    }
}
