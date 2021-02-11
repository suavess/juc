package com.suave.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Suave
 * @date 2021/2/11 4:34 下午
 */
@SuppressWarnings("all")
public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // FutureTask是Runnable的实现类，同时构造方法可以传入Callable接口
        // Callable会阻塞
        MyThread myThread = new MyThread();
        FutureTask<Integer> task = new FutureTask<>(myThread);
        new Thread(task, "A").start();
        // 只会打印一遍call，结果被缓存，提高效率
        new Thread(task, "B").start();
        Integer result = task.get();
        System.out.println("1->" + result);
        Integer result2 = task.get();
        System.out.println("2->" + result2);
    }
}

class MyThread implements Callable<Integer> {
    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
        System.out.println("call");
        return 123456;
    }
}
