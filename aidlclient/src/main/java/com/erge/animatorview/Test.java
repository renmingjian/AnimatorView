package com.erge.animatorview;

/**
 * Created by erge 1/18/21 11:36 AM
 */
public class Test {
    private int a = 0;

    public void add(int add) {
        for (int i = 0; i < 10000; i++) {
            a += add;
            System.out.println("thread = " + Thread.currentThread() + "--a = " + a);
        }
    }

    public static void main(String[] args) {
        final Test test = new Test();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.add(1);
            }
        });
        thread1.setName("线程a");
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test.add(1);
            }
        });
        thread2.setName("线程b");
        thread2.start();
    }

}
