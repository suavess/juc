package com.suave.add;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author Suave
 * @date 2021/2/11 7:37 下午
 */
@SuppressWarnings("all")
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        // 构造方法 计数，Runnable
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("召唤神龙成功！");
        });
        for (int i = 0; i < 7; i++) {
            final int tmp = i;
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "收集第" + tmp + "颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "->执行完毕");
            }).start();
        }
    }
}
