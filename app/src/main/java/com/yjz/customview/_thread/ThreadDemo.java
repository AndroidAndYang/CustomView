package com.yjz.customview._thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author： YJZ
 * date:  2019/9/19
 * des:
 */
public class ThreadDemo {

    public static void main(String[] args) {
        // thread();
        // runnable();
        // threadFactory();
        // executor();
        // callable();
        // new CallAbleDemo().start();
        // runSynchronized1Demo();
        // runSynchronized2Demo();
        runSynchronized3Demo();
    }

    private static void runSynchronized3Demo() {
        new Synchronized3Demo().runTest();
    }

    private static void runSynchronized2Demo() {
        new Synchronized2Demo().runTest();
    }

    private static void runSynchronized1Demo() {
        new Synchronized1Demo().runTest();
    }

    private static void callable() {

        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "done";
            }
        };
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> future = executorService.submit(callable);
        try {
            String result = future.get();
            System.out.println("result = " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void executor() {
        /*
                缓存性池子，先查看池中有没有以前创建的线程，如果有就reuse,如果没有就会新建一个线程加入池中。
            缓存型池子中通常用于执行一些生存期很短的异步性任务。
         */
        final ExecutorService executor = Executors.newCachedThreadPool();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    if (executor.isShutdown()) {
                        System.out.println("Runnable isShutdown!");
                        return;
                    }
                    System.out.println("Runnable started!" + i);
                }
            }
        };
        // 启动线程
        executor.execute(runnable);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 终止线程
        executor.shutdown();
    }

    /**
     * threadFactory 创建多个线程
     */
    private static void threadFactory() {
        ThreadFactory threadFactory = new ThreadFactory() {
            AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "Thread-" + count.incrementAndGet());
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " started!");
            }
        };


        for (int i = 0; i < 10; i++) {
            // 创建线程
            Thread thread = threadFactory.newThread(runnable);
            thread.start();
        }
    }

    /**
     * 使用 Runnable 类来定义工作
     */
    private static void runnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable started!");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * 使用 Thread 类来定义工作
     */
    private static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                // super.run(); 判断的是初始化Thread类的时候是否有传入Runnable的构造参数，如果不为空，会调用Runnable中的run方法。
                super.run();
                System.out.println("Thread started!");
            }
        };
        thread.start();
    }
}
