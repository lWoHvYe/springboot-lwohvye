package com.springboot.shiro.shiro2spboot.common.local;

import com.springboot.shiro.shiro2spboot.common.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 抽卡模拟
 * 将抽卡简化成随机取一个1000的样本中的数，取到指定的算抽中
 * 在取到需要的时，会将与其同样的从期望中一并移除
 * <p>
 * 使用多线程时，有时需关注其他线程的完成情况
 * 采用线程的方式  Thread
 */
//@SpringBootTest
public class Sample {

    private Logger logger4j = LoggerFactory.getLogger(Sample.class);
    private volatile int s50 = 0;
    private volatile int s100 = 0;
    private volatile int s150 = 0;
    private volatile int s200 = 0;
    private volatile int s250 = 0;
    private volatile int s300 = 0;
    private volatile int s350 = 0;
    private volatile int s400 = 0;
    private volatile int s450 = 0;
    private volatile int s500 = 0;
    private volatile int other = 0;
    private static final CountDownLatch latch = new CountDownLatch(5);

    public static void main(String[] args) {
        try {
//            记录开始时间
            long start = DateTimeUtil.getCurMilli();
            Sample sample = new Sample();
//            开始模拟
            sample.startWork();
//            因为把结果输出放在主线程，所以需要设计主线程等待其他线程结束
            latch.await();
            sample.printResult();
//            记录结束时间
            long end = DateTimeUtil.getCurMilli();
//            输出模拟用时
            System.out.println(end - start);
        } catch (InterruptedException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟入口
     *
     * @throws NoSuchAlgorithmException
     */
    private void startWork() throws NoSuchAlgorithmException {
//        创建随机数
//        池子集合
        List<List<Integer>> lists = new ArrayList<>();
//        池子1 2% 2% 2.5% 2.5% 2.5%
        List<Integer> list1 = Arrays.asList(20, 20, 25, 25, 25);

//        池子2 2% 1.8% 1.8% 2.5% 5%
        List<Integer> list2 = Arrays.asList(20, 18, 25, 50);
//        将池子放入总集
        lists.add(list1);
        lists.add(list2);

//        开始模拟
        doWork(lists);
    }

    private void printResult() {
        System.out.println("输出结果");
//        Thread thread = new Thread(() -> {
        logger4j.info("50次以内：" + s50 * 0.0001 + "%;");
        logger4j.info("100次以内：" + s100 * 0.0001 + "%;");
        logger4j.info("150次以内：" + s150 * 0.0001 + "%;");
        logger4j.info("200次以内：" + s200 * 0.0001 + "%;");
        logger4j.info("250次以内：" + s250 * 0.0001 + "%;");
        logger4j.info("300次以内：" + s300 * 0.0001 + "%;");
        logger4j.info("350次以内：" + s350 * 0.0001 + "%;");
        logger4j.info("400次以内：" + s400 * 0.0001 + "%;");
        logger4j.info("450次以内：" + s450 * 0.0001 + "%;");
        logger4j.info("500次以内：" + s500 * 0.0001 + "%;");
        logger4j.info("500次以上：" + other * 0.0001 + "%;");
        System.out.println("总计模拟:" + (s50 + s100 + s150 + s200 + s250 + s300 + s350 + s400 + s450 + s500 + other) + "次");
    }

    private synchronized void doWork(List<List<Integer>> lists) throws NoSuchAlgorithmException {
        for (int i = 0; i < 5; i++) {
            SimuThread simuThread = new SimuThread(lists);
            Thread thread = new Thread(simuThread);
            thread.start();
//            System.out.println(thread.getName());
        }
    }

    class SimuThread implements Runnable {

        private List<List<Integer>> lists;

        private Random random = SecureRandom.getInstanceStrong();

        public SimuThread(List<List<Integer>> lists) throws NoSuchAlgorithmException {
            this.lists = lists;
        }

        @Override
        public void run() {
            for (int j = 0; j < 200000; j++) {
//            开始模拟
                int count = simulate(random, lists);
//            记录结果
                if (count <= 50) {
                    s50++;
                } else if (count <= 100) {
                    s100++;
                } else if (count <= 150) {
                    s150++;
                } else if (count <= 200) {
                    s200++;
                } else if (count <= 250) {
                    s250++;
                } else if (count <= 300) {
                    s300++;
                } else if (count <= 350) {
                    s350++;
                } else if (count <= 400) {
                    s400++;
                } else if (count <= 450) {
                    s450++;
                } else if (count <= 500) {
                    s500++;
                } else {
                    other++;
                }
            }
            System.out.println("运行结束");
            latch.countDown();
        }

        /**
         * @Description: 抽卡模拟，核心代码区
         * @Param: [random, lists]
         * @return: int
         * @Author: Hongyan Wang
         * @Date: 2019/9/24 9:59
         */
        private int simulate(Random random, List<List<Integer>> lists) {
            //        抽卡数
            int count = 0;
//              存放目标集合，内部数个子集合
            List<List<Integer>> mblist = new ArrayList<>();
//            存放目标值的集合
            List<Integer> dblist = new ArrayList<>();
            for (List<Integer> list : lists) {
//            生成目标数值的开始值
                int start = 1;
                for (Integer integer : list) {
//                单个子集合
                    List<Integer> zlist = new ArrayList<>();
                    for (int i = 0; i < integer; i++) {
                        zlist.add(start);
                        start++;
                    }
                    mblist.add(zlist);
                    dblist.addAll(zlist);
                }
//            当目标值不为空时进行抽卡

                if (!dblist.isEmpty()) do {
//            开始抽卡
                    int result = random.nextInt(1000) + 1;
//                判断是否抽到目标卡
                    //                    当抽到目标卡时，遍历目标子集合
                    if (dblist.contains(result)) //                        判断目标是否在子集合中
                        for (List<Integer> integerList : mblist)
                            //                            当目标在子集合中时，从目标集合中移除对应子集合内容
                            if (integerList.contains(result)) dblist.removeAll(integerList);
//                抽卡次数+1
                    count++;
                } while (!dblist.isEmpty());
//            抽完一池，置空子集合
                mblist.clear();
            }
            return count;
        }
    }
}
