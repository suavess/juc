package com.suave.add;

import java.util.concurrent.CountDownLatch;

/**
 * 减法计数器
 *
 * @author Suave
 * @date 2021/2/11 7:18 下午
 */
@SuppressWarnings("all")
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " -> go out");
                countDownLatch.countDown();
                System.out.println(countDownLatch.getCount());
            }, String.valueOf(i)).start();
        }

        // 等待计数器归零
        countDownLatch.await();

        System.out.println("关门");
    }
}
