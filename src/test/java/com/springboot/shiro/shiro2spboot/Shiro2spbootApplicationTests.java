package com.springboot.shiro.shiro2spboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Shiro2spbootApplicationTests {

    @Test
    public void contextLoads() {
        String result = """
        select "paremeterName" from "testTable"
    """;
        String testJson;
        testJson = """
        {
        "paramName":"Name",
        "paramValue":{
            "FirstName":"SpringBoot",
            "LastName":"Shiro"
         },
         "ArrayList":[
            {'a':1},
            {'b':2},
            {'c':3}
         ]
        }
     """;
    }

}
