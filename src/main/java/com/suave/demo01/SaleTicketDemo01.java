package com.suave.demo01;

import java.util.concurrent.TimeUnit;

/**
 * @author Suave
 * @date 2021/2/10 1:46 下午
 */
@SuppressWarnings("all")
public class SaleTicketDemo01 {
    public static void main(String[] args) {
        // 并发：多线程操作同一个资源类，把资源类丢入线程
        Ticket ticket = new Ticket();
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
class Ticket {

    private int number = 50;

    public synchronized void sale() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出了第" + number-- + "票，剩余：" + number);
        }
    }
}
