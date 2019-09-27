package com.springboot.shiro.shiro2spboot;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot
 * @className TestThread
 * @description 分别测试多线程单变量，和多线程多变量的情况
 * @date 2019/9/27 8:32
 */
public class TestThread {

    private Integer addInt = 0;
    private Integer reduceInt = 0;
    private static Integer threadCount = 100;
    private static CountDownLatch downLatch = new CountDownLatch(threadCount);

    @Test
    public void testThread() throws InterruptedException {
        ExecutorService executorService = Executors.newWorkStealingPool();
        UpdateNum updateNum = new UpdateNum();
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(updateNum);
        }
        executorService.shutdown();
        downLatch.await();
        System.out.println(addInt);
    }

    /**
     * @author Hongyan Wang
     * @description
     * @params
     * @return
     * @date 2019/9/27 8:38
     */
    public class UpdateNum implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                if (i % 2 == 0) {
                    synchronized (this) {
                        addInt++;
                    }
                } else {
                    synchronized (this) {
                        addInt--;
                    }
                }
            }
            synchronized (this) {
                System.out.println(Thread.currentThread().getName() + " : 执行完毕 : " + addInt);
            }
            downLatch.countDown();
//            synchronized ()
        }
    }
}
