package com.suave.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程间同步问题
 *
 * @author Suave
 * @date 2021/2/10 4:38 下午
 */
@SuppressWarnings("all")
public class B {
    public static void main(String[] args) {
        DataB dataB = new DataB();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                dataB.increment();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                dataB.decrement();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                dataB.increment();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                dataB.decrement();
            }
        }, "D").start();
    }
}

class DataB {
    private int number = 0;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void increment() {
        // 用while不用if 防止线程虚假唤醒的问题
        // 当大于两个线程进入时，C线程调用notify释放锁，A B线程都被唤醒，都开始抢锁，此时A抢到锁 B线程处于就绪状态等待锁，
        // A进行了++操作，结束后释放锁，B就绪状态立即抢到锁，B就开始执行++操作，就会有线程不安全的问题
        lock.lock();
        try {
            while (number != 0) {
                // 等待
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知其他线程加一完毕
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public synchronized void decrement() {
        lock.lock();
        try {
            // 用while不用if 防止线程虚假唤醒的问题
            while (number == 0) {
                // 等待
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知其他线程减一完毕
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
