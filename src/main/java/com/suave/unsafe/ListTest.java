package com.suave.unsafe;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * java.util.ConcurrentModificationException 并发修改异常
 *
 * @author Suave
 * @date 2021/2/11 12:45 下午
 */
@SuppressWarnings("all")
public class ListTest {
    public static void main(String[] args) {
        /**
         * 并发下ArrayList线程不安全
         * 解决方案：
         * 1.List<String> list = new Vector<>();
         * 2.Collections.synchronizedList
         * 3.List<String> list = new CopyOnWriteArrayList<>();
         * CopyOnWrite 写入时复制 多线程调用时读取时是固定的，写入时覆盖
         */
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
