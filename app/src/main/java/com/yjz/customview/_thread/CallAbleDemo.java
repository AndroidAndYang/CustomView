package com.yjz.customview._thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * author： YJZ
 * date:  2019/9/29
 * des:
 */
public class CallAbleDemo {


    public void start() {

        Callable<ChuJu> onlineShopping = new Callable<ChuJu>() {
            @Override
            public ChuJu call() {

                System.out.println("第一步：下单");
                System.out.println("第二步：等待送货");
                try {
                    Thread.sleep(4000);  // 模拟送货时间
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第三步：快递送达");
                return new ChuJu();
            }
        };
        /*
         *      future的核心思想是：一個方法f,计算过程可能非常耗时，等待f返回，显然不明智，可以在调用f的时候，立马返回一个future。
         *  可以通过future这个数据结果去控制方法的计算过程。
         */
        FutureTask<ChuJu> task = new FutureTask<>(onlineShopping);
        new Thread(task).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ShiCai shiCai = new ShiCai();
        System.out.println(shiCai.buy());
        System.out.println("第二步：食材到位");

        // 判断callable有没有完成
        while (!task.isDone()) {  // 联系快递员，询问是否到货
            // System.out.println("第三步：厨具还没到，心情好就等着（心情不好就取消订单）");
        }

        try {
            ChuJu chuJu = task.get();
            cook(chuJu, shiCai);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class ChuJu {
    }

    private class ShiCai {

        private String buy() {
            return "第一步：购买食材";
        }
    }

    private void cook(ChuJu chuJu, ShiCai shiCai) {
        System.out.println("开始cook");
    }
}
