package com.yjz.customview._thread;

/**
 * author： YJZ
 * date:  2019/10/8
 * des:
 */
public class Synchronized1Demo implements TestDemo {

    private int x = 0;
    private int y = 0;

    private synchronized void count(int value) {
        x = value;
        y = value;
        if (x != y) {
            System.out.println("x: " + x + ", y:" + y);
        }
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_0; i++) {
                    count(i);
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_0; i++) {
                    count(i);
                }
            }
        }.start();
    }
}
