package com.springboot.shiro.shiro2spboot;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @PackageName: com.springboot.shiro.shiro2spboot
 * @ClassName: TestJDK
 * @Description:
 * @Author: Hongyan Wang
 * @Date: 2019/9/23 16:14
 */
public class TestJDK {

    @Test
    public void testTextBlocks() {
//        String text = """
//         {
//            "code": "200",
//            "data": {
//            "rows":[{
//                "cameraId": "100",
//                "cameraType": 0,
//                "createTime": "2019-03-05",
//                "decodetag": "dahua",
//                "extraField": {},
//                "indexCode": "1c0de121-2ce9-4b9f-b58e-bec34f66c5b2",
//                "isOnline": 1,
//                "latitude": "18.2557",
//                "longitude": "135.6358",
//                "name": "一个卡口",
//                "pixel": 1,
//                "updateTime": "2019-04-18"
//            }, {
//                "cameraId": "1004",
//                "cameraType": 0,
//                "createTime": "2018-07-05",
//                "decodetag": "dahua",
//                "extraField": {},
//                "indexCode": "63717de5-dd4b-4682-ae45-284d600d6cd3",
//                "isOnline": 1,
//                "latitude": "49.2123",
//                "longitude": "63.6311",
//                "name": "又一个球机",
//                "pixel": 1,
//                "updateTime": "2019-01-01"
//            }, {
//                "cameraId": "101",
//                "cameraType": 0,
//                "createTime": "2017-06-06",
//                "decodetag": "hikvision",
//                "extraField": {},
//                "indexCode": "",
//                "isOnline": 1,
//                "latitude": "47.2438",
//                "longitude": "89.6809",
//                "name": "一个球机",
//                "pixel": 1,
//                "updateTime": "2017-10-05"
//            }, {
//                "cameraId": "1063",
//                "cameraType": 0,
//                "createTime": "2019-08-15",
//                "decodetag": "hikvision",
//                "extraField": {},
//                "indexCode": "",
//                "isOnline": 1,
//                "latitude": "12.2375",
//                "longitude": "37.6782",
//                "name": "一个枪机",
//                "pixel": 1,
//                "updateTime": "2019-10-01"
//            }],
//                "page": 0,
//                "size": 4000,
//                "total": 2877
//            },
//            "msg": "成功"
//        }
//            """;
    }

    @Test
    public void testStream() throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        Supplier<Integer> runnable = random::nextInt;

        Function<String, Integer> function = Integer::parseInt;
        Integer apply = function.apply(String.valueOf(runnable.get()));
        System.out.println(apply);

        Consumer<List<Integer>> consumer = e -> Stream.generate(runnable).limit(10).forEach(System.out::println);
        List<Integer> integerList = Arrays.asList(1,2,3,4,5,6,7,8,9,0,11,14);
        consumer.accept(integerList);
    }
}
