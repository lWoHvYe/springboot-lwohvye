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

/**
 * 抽卡模拟
 * 将抽卡简化成随机取一个1000的样本中的数，取到指定的算抽中
 * 在取到需要的时，会将与其同样的从期望中一并移除
 * <p>
 * 使用多线程时，有时需关注其他线程的完成情况
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
    //    模拟线程完成个数
    private static volatile int simu = 0;
    //    统计线程完成个数
    private static volatile int han = 0;

    private volatile List<Integer> countList = new ArrayList<>(1000000);

    private Random random = SecureRandom.getInstanceStrong();

    public Sample() throws NoSuchAlgorithmException {
    }

    public static void main(String[] args) {
        try {
            long start = DateTimeUtil.getCurMilli();
            Sample sample = new Sample();
            sample.test();
//            因为把结果输出放在主线程，所以需要设计主线程等待其他线程结束
            while (simu < 5)
                Thread.sleep(10000);
            sample.handleList(5);
            while (han < 5)
                Thread.sleep(10000);
            sample.printResult();
            long end = DateTimeUtil.getCurMilli();
            System.out.println(end - start);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test() {
//        创建随机数
//        池子集合
        List<List<Integer>> lists = new ArrayList<>();
//        池子1 2% 2% 2.5% 2.5% 2.5%
        List<Integer> list1 = Arrays.asList(20, 20, 25, 25, 25);

//        池子2 2% 1.8% 1.8% 2.5% 5%
        List<Integer> list2 = Arrays.asList(20, 18, 25, 50);
        lists.add(list1);
        lists.add(list2);
//        初始化结果
////        ThreadLocal<Integer> s50 = ThreadLocal.withInitial(() -> 0);
//        ThreadLocal<Integer> s100 = ThreadLocal.withInitial(() -> 0);
//        ThreadLocal<Integer> s150 = ThreadLocal.withInitial(() -> 0);
//        ThreadLocal<Integer> s200 = ThreadLocal.withInitial(() -> 0);
//        ThreadLocal<Integer> s250 = ThreadLocal.withInitial(() -> 0);
//        ThreadLocal<Integer> s300 = ThreadLocal.withInitial(() -> 0);
//        ThreadLocal<Integer> s350 = ThreadLocal.withInitial(() -> 0);
//        ThreadLocal<Integer> s400 = ThreadLocal.withInitial(() -> 0);
//        ThreadLocal<Integer> s450 = ThreadLocal.withInitial(() -> 0);
//        ThreadLocal<Integer> s500 = ThreadLocal.withInitial(() -> 0);
//        ThreadLocal<Integer> other = ThreadLocal.withInitial(() -> 0);

        doWork(lists);
//        });
//        thread.start();
//        System.out.println(thread.getName());
    }


    /**
     * 输出模拟结果
     */
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

    /**
     * 启动模拟多线程
     *
     * @param lists
     */
    private synchronized void doWork(List<List<Integer>> lists) {
        for (int i = 0; i < 5; i++) {
            SimuThread simuThread = new SimuThread(lists);
            Thread thread = new Thread(simuThread);
            thread.start();
//            System.out.println(thread.getName());
        }
    }

    /**
     * 模拟线程
     */
    class SimuThread implements Runnable {

        private List<List<Integer>> lists;

        public SimuThread(List<List<Integer>> lists) {
            this.lists = lists;
        }

        @Override
        public void run() {
            List<Integer> cotList = new ArrayList<>(200000);
            for (int j = 0; j < 200000; j++) {
//            开始模拟
                int count = simulate(random, lists);
//                将模拟结果放入集合中
                cotList.add(count);
            }
//            单进程结束，将结果放入公共集合
            countList.addAll(cotList);
//            记录结果

            System.out.println("运行结束");
            simu++;
        }

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
                while (!dblist.isEmpty()) {
//            开始抽卡
                    int result = random.nextInt(1000) + 1;
//                判断是否抽到目标卡
                    if (dblist.contains(result)) {
//                    当抽到目标卡时，遍历目标子集合
                        for (List<Integer> integerList : mblist) {
//                        判断目标是否在子集合中
                            if (integerList.contains(result)) {
//                            当目标在子集合中时，从目标集合中移除对应子集合内容
                                dblist.removeAll(integerList);
                            }
                        }
                    }
//                抽卡次数+1
                    count++;
                }
//            抽完一池，置空子集合
                mblist.clear();
            }
            return count;
        }
    }

    /**
     * 处理模拟结果，开启多线程
     *
     * @param threadNum
     */
    public synchronized void handleList(int threadNum) {
        int length = countList.size();
        int tl = length % threadNum == 0 ? length / threadNum : (length
                / threadNum + 1);

        for (int i = 0; i < threadNum; i++) {
            int end = (i + 1) * tl;
//            给线程分工
            HandleThread thread = new HandleThread("线程[" + (i + 1) + "] ", countList, i * tl, end > length ? length : end);
            thread.start();
        }
    }

    /**
     * 处理模拟结果线程
     */
    class HandleThread extends Thread {
        private String threadName;
        private List<Integer> data;
        private int start;
        private int end;

        public HandleThread(String threadName, List<Integer> data, int start, int end) {
            this.threadName = threadName;
            this.data = data;
            this.start = start;
            this.end = end;
        }

        public void run() {
            List<Integer> subList = data.subList(start, end)/*.add("^&*")*/;
            System.out.println(threadName + "处理了" + subList.size() + "条！");
            countNumber(subList);
            han++;
        }

        /**
         * 统计模拟结果
         *
         * @param subList
         */
        private void countNumber(List<Integer> subList) {
            for (Integer count : subList) {
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
        }
    }

}
