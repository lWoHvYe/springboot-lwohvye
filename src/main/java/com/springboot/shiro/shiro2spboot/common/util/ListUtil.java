package com.springboot.shiro.shiro2spboot.common.util;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.common.util
 * @className ListUtil
 * @description 使用Stream分割list为多个list
 * @date 2020/1/15 15:48
 */

public class ListUtil<T> {

    //按每n个一组分割
    private Integer maxNumber;
//    结果
    @Getter
    private List<List<T>> resultList;
//    切割的List
    private List<T> subList;

    public ListUtil(Integer maxNumber, List<T> subList) {
        this.maxNumber = maxNumber;
        this.subList = subList;
        subList();
    }

    /**
     * 计算切分次数
     */
    private Integer countStep(Integer size) {
        return (size + maxNumber - 1) / maxNumber;
    }

    private void subList() {
        int limit = countStep(subList.size());

        //方法一：使用流遍历操作，获取指定索引范围数据，放入结果集中
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> resultList.add(subList.stream().skip(i * maxNumber).limit(maxNumber).collect(Collectors.toList())));
        System.out.println(resultList);

        //方法二：获取分割后的集合，将指定索引范围内数据创建集合，放入结果集
        resultList = Stream.iterate(0, n -> n + 1).limit(limit).parallel().map(a -> subList.stream().skip(a * maxNumber).limit(maxNumber).parallel().collect(Collectors.toList())).collect(Collectors.toList());
        System.out.println(resultList);
    }
}
