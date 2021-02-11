package com.suave.unsafe;

import java.util.HashMap;
import java.util.UUID;

/**
 * java.util.ConcurrentModificationException 并发修改异常
 *
 * @author Suave
 * @date 2021/2/11 4:08 下午
 */
@SuppressWarnings("all")
public class MapTest {
    public static void main(String[] args) {
        /**
         * 并发下HashMap线程不安全
         * 解决方案：
         * 1.Collections.synchronizedMap
         * 2.Map<String,Object> map = new ConcurrentHashMap<>();
         */
        // HashMap构造方法的两个参数 初始容量 16 加载因子 0.75
        HashMap<String, Object> map = new HashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
