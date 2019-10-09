package com.yjz.customview._thread;

/**
 * author： YJZ
 * date:  2019/10/8
 * des:
 */
public class Synchronized3Demo implements TestDemo {

    private int x, y = 0;

    /**
     * synchronized 保证在多线程执行时，不会因为线程切换导致结果和预计不一样。
     */
    private void count(int value) {
        x = value;
        y = value;
    }

    @Override
    public void runTest() {

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_00; i++) {
                    count(i);
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_00; i++) {
                    count(i);
                }
            }
        }.start();

    }
}
