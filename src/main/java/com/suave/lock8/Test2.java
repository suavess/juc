package com.suave.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 3.增加一个hello普通方法后，先打印sendSms还是Hello
 * 4.两个对象，两个同步方法，先打印sendSms还是call
 * 普通方法无需等待锁
 * 两个对象有两把锁
 *
 * @author Suave
 * @date 2021/2/10 9:52 下午
 */
@SuppressWarnings("all")
public class Test2 {
    public static void main(String[] args) {
        // 两个对象
        Phone2 phone = new Phone2();
        Phone2 phone2 = new Phone2();

        new Thread(() -> {
            phone.sendSms();
        }, "A").start();
//        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
//            phone.hello();
            phone2.call();
        }, "B").start();
    }
}

class Phone2 {

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

    public void hello() {
        System.out.println("hello");
    }

}
