package com.suave.pc;

/**
 * 线程之间的通信问题 生产者与消费者
 * 线程交替执行 A B 操作同一个变量
 *
 * @author Suave
 * @date 2021/2/10 3:20 下午
 */
@SuppressWarnings("all")
public class A {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}

class Data {

    private int number = 0;

    public synchronized void increment() throws InterruptedException {
        // 用while不用if 防止线程虚假唤醒的问题
        // 当大于两个线程进入时，C线程调用notify释放锁，A B线程都被唤醒，都开始抢锁，此时A抢到锁 B线程处于就绪状态等待锁，
        // A进行了++操作，结束后释放锁，B就绪状态立即抢到锁，B就开始执行++操作，就会有线程不安全的问题
        while (number != 0) {
            // 等待
            this.wait();
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        // 通知其他线程加一完毕
        this.notify();
    }

    public synchronized void decrement() throws InterruptedException {
        // 用while不用if 防止线程虚假唤醒的问题
        while (number == 0) {
            // 等待
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        // 通知其他线程减一完毕
        this.notify();
    }

}