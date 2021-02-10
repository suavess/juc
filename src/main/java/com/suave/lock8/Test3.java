package com.suave.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 5.增加两个静态的同步方法
 * 6.两个对象，增加两个静态的同步方法
 * 加static后变为静态方法，静态方法锁的是Class类对象
 *
 * @author Suave
 * @date 2021/2/10 10:05 下午
 */
@SuppressWarnings("all")
public class Test3 {
    public static void main(String[] args) {

        Phone3 phone = new Phone3();
        Phone3 phone2 = new Phone3();

        new Thread(() -> {
//            Phone3.sendSms();
            phone.sendSms();
        }, "A").start();
        new Thread(() -> {
//            Phone3.call();
            phone2.call();
        }, "B").start();
    }
}

class Phone3 {

    public static synchronized void sendSms() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("sendSms");
    }

    public static synchronized void call() {
        System.out.println("call");
    }

}
