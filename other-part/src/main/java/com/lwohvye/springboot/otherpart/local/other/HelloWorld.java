package com.lwohvye.springboot.otherpart.local.other;

import lombok.extern.slf4j.Slf4j;

import java.io.PrintStream;
import java.util.function.Consumer;

/**
 * @author Hongyan Wang
 * @packageName com.lwohvye.springboot.otherpart.local.other
 * @className HelloWorld
 * @description
 * @date 2020/12/15 12:54
 */
@Slf4j
public class HelloWorld {
    public static void main(String[] args) {
        var printStream = new PrintStream(System.out);
        printStream.println("Hello World");

        Consumer<String> handler = System.out::println;
        handler.accept("Hello World");
    }
}
