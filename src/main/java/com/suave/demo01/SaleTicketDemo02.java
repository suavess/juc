package com.suave.demo01;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Suave
 * @date 2021/2/10 2:11 下午
 */
@SuppressWarnings("all")
public class SaleTicketDemo02 {
    public static void main(String[] args) {
        // 并发：多线程操作同一个资源类，把资源类丢入线程
        Ticket02 ticket = new Ticket02();
        // @FunctionalInterface 函数式接口 lambda表达式
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket.sale();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket.sale();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ticket.sale();
            }
        }, "C").start();
    }
}

/**
 * 资源类 OOP
 */
class Ticket02 {

    /**
     * 可重入锁
     * 默认未非公平锁
     */
    Lock lock = new ReentrantLock();

    private int number = 50;

    public synchronized void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了第" + number-- + "票，剩余：" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
