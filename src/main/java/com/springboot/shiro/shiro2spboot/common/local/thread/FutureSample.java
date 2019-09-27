package com.springboot.shiro.shiro2spboot.common.local.thread;

import com.springboot.shiro.shiro2spboot.common.util.DateTimeUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.common.local
 * @className FutureSample
 * @description 抽卡模拟 将抽卡简化成随机取一个1000的样本中的数，取到指定的算抽中
 * 在取到需要的时，会将与其同样的从期望中一并移除
 * 由于模拟采用了随机数的方式，所以池子可以任意配置，不影响结果
 * 由于使用了多线程，所以需关注其他线程的完成情况
 * 采用Feature的方式，使用CompletableFuture的runAsync()构建没有返回的子线程，各子线程实时共享数据，使用synchronized同步代码块
 * 需尤其注意变量的作用范围问题
 * 已查明线程安全问题原因，对于使用该方式创建的线程，当存在多个需共享的变量时，使用同步代码块，需使用synchronized(this)
 * @date 2019/9/22 8:54
 */
//TODO 使用CompletableFuture,子线程实时共享数据，使用synchronized同步代码块，不能使用volatile
//@SpringBootTest
public class FutureSample {

    private Logger logger4j = LoggerFactory.getLogger(FutureSample.class);

    private Integer s50 = 0;
    private Integer s100 = 0;
    private Integer s150 = 0;
    private Integer s200 = 0;
    private Integer s250 = 0;
    private Integer s300 = 0;
    private Integer s350 = 0;
    private Integer s400 = 0;
    private Integer s450 = 0;
    private Integer s500 = 0;
    private Integer other = 0;


    /**
     * @return void
     * @description 方法主体，用于模拟调用，获取及输出模拟结果
     * @params []
     * @author Hongyan Wang
     * @date 2019/9/23 9:59
     */
    @Test
    public void startWork() {
        //   开启模拟线程数
        var threadCount = 1000;
//        模拟次数
        var simCount = 1000000;
//        记录开始时间
        var start = DateTimeUtil.getCurMilli();
//        创建随机数
//        池子集合
        var lists = new ArrayList<List<Integer>>();
//        池子1 2% 2% 2.5% 2.5% 2.5%
        var list1 = Arrays.asList(20, 20, 25, 25, 25);

//        池子2 2% 1.8% 1.8% 2.5% 5%
        var list2 = Arrays.asList(20, 18, 18, 25, 50);
//        池子3 2% 2% 2.5% 5%
//        List<Integer> list3 = Arrays.asList(20, 20, 25, 50);
//        可以根据需求调整池子，将概率乘以1000即为预放入集合中的值，之后需要把池子放入总集
        lists.add(list1);
        lists.add(list2);
//        lists.add(list3);
//        设置模拟池子
        var simCallable = new SimCallable(lists, simCount / threadCount);
//        创建线程数组
        var futuresArray = new CompletableFuture[threadCount];
//            开启模拟线程，使用线程池的方式创建CompletableFuture
        for (int i = 0; i < threadCount; i++) {
//            创建模拟线程
            var future = CompletableFuture.runAsync(simCallable);
//            将线程放入线程数组
            futuresArray[i] = future;
        }

//        设置需等待的子线程
        var result = CompletableFuture.allOf(futuresArray);
//        等待线程完成
        result.join();

//         输出总结果
//        futureSample.printResult(countMap, simCount / 100);
//         记录结束时间
        var end = DateTimeUtil.getCurMilli();
//          输出结果
        logger4j.info("50次以内：" + (double) s50 * 100 / simCount + "%;");
        logger4j.info("100次以内：" + (double) s100 * 100 / simCount + "%;");
        logger4j.info("150次以内：" + (double) s150 * 100 / simCount + "%;");
        logger4j.info("200次以内：" + (double) s200 * 100 / simCount + "%;");
        logger4j.info("250次以内：" + (double) s250 * 100 / simCount + "%;");
        logger4j.info("300次以内：" + (double) s300 * 100 / simCount + "%;");
        logger4j.info("350次以内：" + (double) s350 * 100 / simCount + "%;");
        logger4j.info("400次以内：" + (double) s400 * 100 / simCount + "%;");
        logger4j.info("450次以内：" + (double) s450 * 100 / simCount + "%;");
        logger4j.info("500次以内：" + (double) s500 * 100 / simCount + "%;");
        logger4j.info("500次以上：" + (double) other * 100 / simCount + "%;");
        System.out.println("总计模拟:" + (s50 + s100 + s150 + s200 + s250 + s300 + s350 + s400 + s450 + s500 + other) + "次");

        System.out.println(end - start);
    }

    /**
     * @author Hongyan Wang
     * @description 模拟多线程相关类
     * @className SimCallable
     * @date 2019/9/23 9:53
     */
    class SimCallable implements Runnable {
        //        卡池集
        private List<List<Integer>> lists;
        //        总模拟次数
        private Integer simCount;

        private SimCallable(List<List<Integer>> lists, Integer simCount) {
            this.lists = lists;
            this.simCount = simCount;
        }

        /**
         * @return void
         * @description 不再使用同步变量，直接将各子线程结果返回，由主线程处理,
         * 池子是否乱序并不影响结果，若每次模拟都重新生成乱序池子将大幅降低效率，可以一个线程只使用一个乱序池子，但实际意义不大
         * @params []
         * @author Hongyan Wang
         * @date 2019/9/23 9:52
         */
        @Override
        public void run() {
            try {
                var random = SecureRandom.getInstanceStrong();
//              生成乱序池子
                var ranArray = ranArray();
                for (int j = 0; j < simCount; j++) {
//                开始模拟
                    var count = simulateWork(random, lists, ranArray);
                    //TODO 后续需对统计进行优化
//                将模拟结果放入集合中，这里用的synchronized(this)，需注意，当多变量时，里面放变量名，可能出现线程安全问题
                    if (count <= 50) synchronized (this) {
                        s50++;
                    }
                    else if (count <= 100) synchronized (this) {
                        s100++;
                    }
                    else if (count <= 150) synchronized (this) {
                        s150++;
                    }
                    else if (count <= 200) synchronized (this) {
                        s200++;
                    }
                    else if (count <= 250) synchronized (this) {
                        s250++;
                    }
                    else if (count <= 300) synchronized (this) {
                        s300++;
                    }
                    else if (count <= 350) synchronized (this) {
                        s350++;
                    }
                    else if (count <= 400) synchronized (this) {
                        s400++;
                    }
                    else if (count <= 450) synchronized (this) {
                        s450++;
                    }
                    else if (count <= 500) synchronized (this) {
                        s500++;
                    }
                    else synchronized (this) {
                            other++;
                        }
                }
            } catch (NoSuchAlgorithmException e) {
                logger4j.info(e.getMessage());
            }
        }

        /**
         * @return int[]
         * @description 生成乱序不重复数组，作为模拟池
         * @params []
         * @author Hongyan Wang
         * @date 2019/9/23 9:51
         */
        private int[] ranArray() {
            var ranArrays = new int[1000];
            try {
                for (int i = 0; i < 1000; i++) ranArrays[i] = i + 1;
                var r = SecureRandom.getInstanceStrong();
                for (int i = 0; i < 1000; i++) {
                    var in = r.nextInt(1000 - i) + i;
                    var t = ranArrays[in];
                    ranArrays[in] = ranArrays[i];
                    ranArrays[i] = t;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return ranArrays;
        }

        /**
         * @return int
         * @description 核心代码
         * 模拟抽卡，当前为单个池子，根据要求，生成数个不重复的数值集合，
         * 当结果在某个数值集合中时，从目标集合中移除其所在的集合
         * 当前使用连续生成数值的方式
         * @params [random, lists, ranArray]
         * @author Hongyan Wang
         * @date 2019/9/23 9:39
         */
        private int simulateWork(Random random, List<List<Integer>> lists, int[] ranArray) {
            //        抽卡数
            var count = 0;
//                存放单个池子目标集合，内部数个子集合
            var multiList = new ArrayList<List<Integer>>();
//                存放单个池子目标值的集合，主要为了避免每次模拟都要对multiList进行两次遍历
            var numList = new ArrayList<Integer>();
//            对池子进行模拟，抽完一个之后，再抽下一个
            for (var list : lists) {
//                  生成目标数值的开始值
                var index = 1;
                for (var integer : list) {
//                      单个子集合
                    var singleList = new ArrayList<Integer>();
                    for (int i = 0; i < integer; i++) {
                        singleList.add(ranArray[index]);
                        index++;
                    }
                    numList.addAll(singleList);
                    multiList.add(singleList);
                }
                //TODO 模拟部分是花费时间最多的地方，是主要的优化部分
//            当目标值不为空时进行抽卡
                while (!numList.isEmpty()) {
//            开始抽卡
                    var result = random.nextInt(1000) + 1;
//                判断是否抽到目标卡
                    if (numList.contains(result))
//                    当抽到目标卡时，遍历目标子集合
                        for (var integerList : multiList)
//                        判断目标是否在子集合中
                            if (integerList.contains(result))
//                            当目标在子集合中时，从目标集合中移除对应子集合内容
                                numList.removeAll(integerList);
//                抽卡次数+1
                    count++;
                }
//            抽完一池，置空子集合
                multiList.clear();
            }
            return count;
        }
    }
}
