package com.springboot.shiro.shiro2spboot.common.local;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 抽卡模拟
 * 将抽卡简化成随机取一个1000的样本中的数，取到指定的算抽中
 * 在取到需要的时，会将与其同样的从期望中一并移除
 */
@SpringBootTest
public class Sample {
    @Test
    public void Test() {
//        池子集合
        List<List<Integer>> lists = new ArrayList<>();
//        池子1 2% 2% 2.5% 2.5% 2.5%
        List<Integer> list1 = Arrays.asList(20, 20, 25, 25, 25);

//        池子2 2% 1.8% 1.8% 2.5% 5%
        List<Integer> list2 = Arrays.asList(20, 18, 25, 50);
        lists.add(list1);
        lists.add(list2);
//        初始化结果
        int s50 = 0, s100 = 0, s150 = 0, s200 = 0, s250 = 0, s300 = 0, s350 = 0, s400 = 0, s450 = 0, s500 = 0, other = 0;

        for (int j = 0; j < 1000000; j++) {
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
                    Random random = new Random();
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
//            记录结果
            if (count <= 50) {
                s50++;
            } else if (count > 50 && count <= 100) {
                s100++;
            } else if (count > 100 && count <= 150) {
                s150++;
            } else if (count > 150 && count <= 200) {
                s200++;
            } else if (count > 200 && count <= 250) {
                s250++;
            } else if (count > 250 && count <= 300) {
                s300++;
            } else if (count > 300 && count <= 350) {
                s350++;
            } else if (count > 350 && count <= 400) {
                s400++;
            } else if (count > 400 && count <= 450) {
                s450++;
            } else if (count > 450 && count <= 500) {
                s500++;
            } else {
                other++;
            }
        }
        System.out.println("50次以内：" + s50 * 0.0001 + "%;");
        System.out.println("100次以内：" + s100 * 0.0001 + "%;");
        System.out.println("150次以内：" + s150 * 0.0001 + "%;");
        System.out.println("200次以内：" + s200 * 0.0001 + "%;");
        System.out.println("250次以内：" + s250 * 0.0001 + "%;");
        System.out.println("300次以内：" + s300 * 0.0001 + "%;");
        System.out.println("350次以内：" + s350 * 0.0001 + "%;");
        System.out.println("400次以内：" + s400 * 0.0001 + "%;");
        System.out.println("450次以内：" + s450 * 0.0001 + "%;");
        System.out.println("500次以内：" + s500 * 0.0001 + "%;");
        System.out.println("500次以上：" + other * 0.0001 + "%;");
    }

}
