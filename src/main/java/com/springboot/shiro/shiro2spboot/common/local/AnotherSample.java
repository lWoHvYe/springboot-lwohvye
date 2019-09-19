package com.springboot.shiro.shiro2spboot.common.local;

import com.springboot.shiro.shiro2spboot.common.util.DateTimeUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 抽卡模拟
 * 将抽卡简化成随机取一个1000的样本中的数，取到指定的算抽中
 * 在取到需要的时，会将与其同样的从期望中一并移除
 * <p>
 * 使用多线程时，有时需关注其他线程的完成情况
 * 采用Feature的方式
 */
//@SpringBootTest
public class AnotherSample {

    private Logger logger4j = LoggerFactory.getLogger(AnotherSample.class);


    @Test
    public void startWork() throws ExecutionException, InterruptedException {
//        存放总结果集
        Map<String, Integer> countMap = new HashMap<>();
//        记录开始时间
        long start = DateTimeUtil.getCurMilli();
        AnotherSample sample = new AnotherSample();
//        创建随机数
//        池子集合
        List<List<Integer>> lists = new ArrayList<>();
//        池子1 2% 2% 2.5% 2.5% 2.5%
        List<Integer> list1 = Arrays.asList(20, 20, 25, 25, 25);

//        池子2 2% 1.8% 1.8% 2.5% 5%
        List<Integer> list2 = Arrays.asList(20, 18, 25, 50);
        lists.add(list1);
        lists.add(list2);
//        设置模拟池子
        SimuCallable simuCallable = new SimuCallable(lists);
//            开启模拟线程，使用线程池的方式创建CompletableFuture
        CompletableFuture<Map<String, Integer>> work1 = CompletableFuture.supplyAsync(simuCallable::call);
        CompletableFuture<Map<String, Integer>> work2 = CompletableFuture.supplyAsync(simuCallable::call);
        CompletableFuture<Map<String, Integer>> work3 = CompletableFuture.supplyAsync(simuCallable::call);
        CompletableFuture<Map<String, Integer>> work4 = CompletableFuture.supplyAsync(simuCallable::call);
        CompletableFuture<Map<String, Integer>> work5 = CompletableFuture.supplyAsync(simuCallable::call);

        CompletableFuture<Void> result = CompletableFuture.allOf(work1, work2, work3, work4, work5);
        result.join();
//        获取各子线程模拟结果
        Map<String, Integer> work1Map = work1.get();
        Map<String, Integer> work2Map = work2.get();
        Map<String, Integer> work3Map = work3.get();
        Map<String, Integer> work4Map = work4.get();
        Map<String, Integer> work5Map = work5.get();

        int s50, s100, s150, s200, s250, s300, s350, s400, s450, s500, other;

        s50 = work1Map.get("s50") + work2Map.get("s50") + work3Map.get("s50") + work4Map.get("s50") + work5Map.get("s50");
        s100 = work1Map.get("s100") + work2Map.get("s100") + work3Map.get("s100") + work4Map.get("s100") + work5Map.get("s100");
        s150 = work1Map.get("s150") + work2Map.get("s150") + work3Map.get("s150") + work4Map.get("s150") + work5Map.get("s150");
        s200 = work1Map.get("s200") + work2Map.get("s200") + work3Map.get("s200") + work4Map.get("s200") + work5Map.get("s200");
        s250 = work1Map.get("s250") + work2Map.get("s250") + work3Map.get("s250") + work4Map.get("s250") + work5Map.get("s250");
        s300 = work1Map.get("s300") + work2Map.get("s300") + work3Map.get("s300") + work4Map.get("s300") + work5Map.get("s300");
        s350 = work1Map.get("s350") + work2Map.get("s350") + work3Map.get("s350") + work4Map.get("s350") + work5Map.get("s350");
        s400 = work1Map.get("s400") + work2Map.get("s400") + work3Map.get("s400") + work4Map.get("s400") + work5Map.get("s400");
        s450 = work1Map.get("s450") + work2Map.get("s450") + work3Map.get("s450") + work4Map.get("s450") + work5Map.get("s450");
        s500 = work1Map.get("s500") + work2Map.get("s500") + work3Map.get("s500") + work4Map.get("s500") + work5Map.get("s500");
        other = work1Map.get("other") + work2Map.get("other") + work3Map.get("other") + work4Map.get("other") + work5Map.get("other");

        countMap.put("s50", s50);
        countMap.put("s100", s100);
        countMap.put("s150", s150);
        countMap.put("s200", s200);
        countMap.put("s250", s250);
        countMap.put("s300", s300);
        countMap.put("s350", s350);
        countMap.put("s400", s400);
        countMap.put("s450", s450);
        countMap.put("s500", s500);
        countMap.put("other", other);
//         输出总结果
        sample.printResult(countMap);
//         记录结束时间
        long end = DateTimeUtil.getCurMilli();
        System.out.println(end - start);
    }


    /**
     * 输出模拟结果
     */
    private void printResult(Map<String, Integer> countMap) {

        System.out.println("输出结果");

        logger4j.info(String.format("50次以内：%s%%;", countMap.get("s50") * 0.00001));
        logger4j.info(String.format("100次以内：%s%%;", countMap.get("s100") * 0.00001));
        logger4j.info(String.format("150次以内：%s%%;", countMap.get("s150") * 0.00001));
        logger4j.info(String.format("200次以内：%s%%;", countMap.get("s200") * 0.00001));
        logger4j.info(String.format("250次以内：%s%%;", countMap.get("s250") * 0.00001));
        logger4j.info(String.format("300次以内：%s%%;", countMap.get("s300") * 0.00001));
        logger4j.info(String.format("350次以内：%s%%;", countMap.get("s350") * 0.00001));
        logger4j.info(String.format("400次以内：%s%%;", countMap.get("s400") * 0.00001));
        logger4j.info(String.format("450次以内：%s%%;", countMap.get("s450") * 0.00001));
        logger4j.info(String.format("500次以内：%s%%;", countMap.get("s500") * 0.00001));
        logger4j.info(String.format("500次以上：%s%%;", countMap.get("other") * 0.00001));
        logger4j.info("总计模拟:" + (countMap.get("s50") + countMap.get("s100") + countMap.get("s150") + countMap.get("s200")
                + countMap.get("s250") + countMap.get("s300") + countMap.get("s350") + countMap.get("s400") + countMap.get("s450")
                + countMap.get("s500") + countMap.get("other")) + "次");
    }


    /**
     * 模拟多线程
     */
    class SimuCallable implements Callable<Map<String, Integer>> {

        private List<List<Integer>> lists;

        public SimuCallable(List<List<Integer>> lists) {
            this.lists = lists;
        }

        /**
         * 不再使用同步变量，直接将各子线程结果返回，由主线程处理
         *
         * @return
         */
        @Override
        public Map<String, Integer> call() {
            int s50 = 0;
            int s100 = 0;
            int s150 = 0;
            int s200 = 0;
            int s250 = 0;
            int s300 = 0;
            int s350 = 0;
            int s400 = 0;
            int s450 = 0;
            int s500 = 0;
            int other = 0;
//          记录本线程模拟结果集
            Map<String, Integer> countHashMap = new HashMap<>();
            try {
                Random random = SecureRandom.getInstanceStrong();
                for (int j = 0; j < 2000000; j++) {
//                开始模拟
                    int count = simulate(random, lists);
//                将模拟结果放入集合中
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
            } catch (NoSuchAlgorithmException e) {
                logger4j.info(e.getMessage());
            }
            System.out.println("计算完成:" + Thread.currentThread().getName()
                    + ":" + s50 + ":" + s100 + ":" + s150 + ":" + s200 + ":" + s250 + ":" + s300
                    + ":" + s350 + ":" + s400 + ":" + s450 + ":" + s500 + ":" + other);
//            将模拟结果放入集合返回
            countHashMap.put("s50", s50);
            countHashMap.put("s100", s100);
            countHashMap.put("s150", s150);
            countHashMap.put("s200", s200);
            countHashMap.put("s250", s250);
            countHashMap.put("s300", s300);
            countHashMap.put("s350", s350);
            countHashMap.put("s400", s400);
            countHashMap.put("s450", s450);
            countHashMap.put("s500", s500);
            countHashMap.put("other", other);
            System.out.println("运行结束");
            return countHashMap;
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
}
