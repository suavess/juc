package com.suave.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 7.一个静态同步方法，一个普通同步方法，一个对象哪个先打印
 * 8.一个静态同步方法，一个普通同步方法，两个对象哪个先打印
 * 静态同步方法锁的是类对象 普通同步方法锁的是对象本身
 *
 * @author Suave
 * @date 2021/2/10 11:37 下午
 */
@SuppressWarnings("all")
public class Test4 {
    public static void main(String[] args) {

        Phone4 phone = new Phone4();
        Phone4 phone2 = new Phone4();

        new Thread(() -> {
            phone.sendSms();
        }, "A").start();
        new Thread(() -> {
            phone2.call();
        }, "B").start();
    }
}

class Phone4 {

    public static synchronized void sendSms() {
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
