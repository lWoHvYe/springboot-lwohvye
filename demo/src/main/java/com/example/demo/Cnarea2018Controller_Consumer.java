package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * @author Hongyan Wang
 * @packageName com.lwohvye.springcloud.springcloudlwohvyeprovider.controller
 * @className Cnarea2018Controller
 * @description 测试Rest方法
 * @date 2020/1/16 15:12
 */
@RequestMapping("/consumer/cnarea")
@RestController
public class Cnarea2018Controller_Consumer {

    private static final String REST_URL_PREFIX = "http://localhost:8080";

    /**
     * 使用 使用restTemplate访问restful接口非常的简单
     * (url, requestMap,ResponseBean.class)这三个参数分别代表 REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * @return String
     * @description 异步测试方法，province为使用逗号分隔的两位的省级区划名
     * @params [province, page, pageSize]
     * @author Hongyan Wang
     * @date 2020/1/16 22:13
     */
    @RequestMapping("/list")
    public String list(String province, String levels, int page, int pageSize) {
        String url = REST_URL_PREFIX + "/cnarea/list";
//      发送带参数的post情况，参数可以使用以下两种方式设置
//        方式一
        //设置请求header信息，Header可不设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        方式一为直接创建MultiValueMap设置
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("province", province);
        params.add("levels", levels);
        params.add("page", page);
        params.add("pageSize", pageSize);
        //封装请求头和内容
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);
        String post = restTemplate.postForObject(url, requestEntity, String.class);
//        方式二
//        方式二为使用MultipartBodyBuilder的buulid()方法创建MultiValueMap
//        官方比较推荐该种方式
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("id", 8);
        builder.part("name", "王红岩");
        MultiValueMap<String, HttpEntity<?>> build = builder.build();
        String postTest = restTemplate.postForObject(REST_URL_PREFIX + "/cnarea/post", build, String.class);
//      测试带参数的get请求
        String getTest = restTemplate.getForObject(REST_URL_PREFIX + "/cnarea/get/" + 10, String.class);
        return post;
    }
}
