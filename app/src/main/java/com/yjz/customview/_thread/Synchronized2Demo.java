package com.yjz.customview._thread;

/**
 * author： YJZ
 * date:  2019/10/8
 * des:
 */
public class Synchronized2Demo implements TestDemo {

    private int x = 0;

    /**
     * synchronized 保证在多线程执行时，不会因为线程切换导致结果和预计不一样。
     */
    private synchronized void count() {
        x++;
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    // 执行该方法时，如果不加上 synchronized 关键字的话，x的值有可能不会为10000
                    count();
                }
                System.out.println("final x from 1: " + x);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    // 执行该方法时，如果不加上 synchronized 关键字的话，x的值有可能不会为20000
                    count();
                }
                System.out.println("final x from 2: " + x);
            }
        }.start();
    }
}
