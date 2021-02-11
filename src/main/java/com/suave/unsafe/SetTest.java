package com.suave.unsafe;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * java.util.ConcurrentModificationException 并发修改异常
 *
 * @author Suave
 * @date 2021/2/11 3:32 下午
 */
@SuppressWarnings("all")
public class SetTest {
    public static void main(String[] args) {
        /**
         * 并发下HashSet线程不安全
         * 解决方案：
         * 1.Collections.synchronizedSet
         * 2.Set<String> set = new CopyOnWriteArraySet<>();
         * CopyOnWriteArraySet 写入时复制 多线程调用时读取时是固定的，写入时覆盖
         */
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                set.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
