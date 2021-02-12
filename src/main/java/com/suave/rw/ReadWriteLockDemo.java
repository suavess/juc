package com.suave.rw;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock
 * 读-读 可以共存
 * 读-写 不能共存
 * 写-写 不能共存
 * 写锁 独占锁 一次只能被一个线程占用
 * 读锁 共享锁 多个线程可以同时占用
 *
 * @author Suave
 * @date 2021/2/12 1:17 下午
 */
@SuppressWarnings("all")
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCacheLock myCache = new MyCacheLock();
        for (int i = 0; i < 5; i++) {
            int tmp = i;
            new Thread(() -> {
                myCache.put(tmp + "", tmp);
            }).start();
        }
        for (int i = 0; i < 5; i++) {
            int tmp = i;
            new Thread(() -> {
                myCache.get(tmp + "");
            }).start();
        }
    }
}

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    public synchronized void put(String key, Object value) {
        System.out.println(Thread.currentThread().getName() + "写入" + key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "写入OK");
    }

    public void get(String key) {
        System.out.println(Thread.currentThread().getName() + "读取" + key);
        map.get(key);
        System.out.println(Thread.currentThread().getName() + "读取OK");
    }
}

// 加锁的缓存
class MyCacheLock {
    private volatile Map<String, Object> map = new HashMap<>();
    // 读写锁
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // 存，写入时只希望同时只有一个线程写入
    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读取" + key);
            map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}