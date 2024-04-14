package Cas2_Threading.Intro;
import java.lang.*;
public class ThreadsIntro {
    // There are two ways to run something in a different thread in Java
    //      1. extend Thread class
    //      2. implement Runnable interface

    public static class MyThread extends Thread{
        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getId());
        }
    }

    public static class MyThreadRunnable implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getId());
        }
    }
    public static void main(String[] args) {
        Thread t1 = new MyThread();
        t1.start(); // CORRECT - this sets up the actual system thread and invokes run() method

        //kako sada sa startujemo kada koristimo drugi nacin
        Thread t2 = new Thread(new MyThreadRunnable());
        t2.start();

        System.out.println("MY main function finished...");

        //u javi se program zavrsava kada se zavrse sve njegove niti


        /*
        mozemo i da nemamo klasu nego na licu mesta
        primer:

        Thread t3 = new Thread(new Runnable(){
            @Override
            public void run(){
                //we can override method run with our logic
            }
        })

         */

    }
}
