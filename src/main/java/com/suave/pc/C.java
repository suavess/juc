package com.suave.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Suave
 * @date 2021/2/10 9:03 下午
 */
@SuppressWarnings("all")
public class C {
    public static void main(String[] args) {
        DataC dataB = new DataC();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                dataB.printA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                dataB.printB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                dataB.printC();
            }
        }, "C").start();
    }
}

@SuppressWarnings("all")
class DataC {
    private int number = 0;
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void printA() {
        // 用while不用if 防止线程虚假唤醒的问题
        // 当大于两个线程进入时，C线程调用notify释放锁，A B线程都被唤醒，都开始抢锁，此时A抢到锁 B线程处于就绪状态等待锁，
        // A进行了++操作，结束后释放锁，B就绪状态立即抢到锁，B就开始执行++操作，就会有线程不安全的问题
        lock.lock();
        try {
            while (number != 0) {
                // 等待
                condition1.await();
            }
            number = 1;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知其他线程加一完毕
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            // 用while不用if 防止线程虚假唤醒的问题
            while (number != 1) {
                // 等待
                condition2.await();
            }
            number = 2;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知其他线程减一完毕
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            // 用while不用if 防止线程虚假唤醒的问题
            while (number != 2) {
                // 等待
                condition3.await();
            }
            number = 0;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知其他线程减一完毕
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
