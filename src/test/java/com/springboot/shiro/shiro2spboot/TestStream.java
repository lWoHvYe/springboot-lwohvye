package com.springboot.shiro.shiro2spboot;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStream {

    public void test(){

        List<Integer> list = Arrays.asList(1,2,3,4,5,6);
        Stream stream = list.stream();
        stream.collect(Collectors.toCollection(Stack::new));
    }
}
