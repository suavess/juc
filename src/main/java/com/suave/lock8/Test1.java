package com.suave.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 8锁就是关于锁的8个问题
 * 1.正常情况下，两个线程是先打印sendSms还是call
 * 2.sendSms先sleep5秒，两个线程是先打印sendSms还是call
 * 哪个线程先拿到Phone资源类的锁就先执行哪个方法
 *
 * @author Suave
 * @date 2021/2/10 9:20 下午
 */
@SuppressWarnings("all")
public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendSms();
        }, "A").start();
//        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            phone.call();
        }, "B").start();
    }
}

class Phone {

    // 两个方法用的是同一个锁，谁先拿到谁先执行
    public synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sendSms");
    }

    public synchronized void call() {
        System.out.println("call");
    }

}
