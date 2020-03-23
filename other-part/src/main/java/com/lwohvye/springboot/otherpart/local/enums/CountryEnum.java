package com.lwohvye.springboot.otherpart.local.enums;

import lombok.Getter;

/**
 * @author Hongyan Wang
 * @packageName com.lwohvye.springboot.otherpart.local.enums
 * @className CountryEnum
 * @description 枚举类实例
 * @date 2020/3/23 10:34
 */
//枚举一般属性固定，所以只提供get方法
@Getter
public enum CountryEnum {
    //定义枚举
    ONE(1, "齐", true), TWO(2, "楚", true), THREE(3, "燕", false),
    FOUR(4, "赵", false), FIVE(5, "魏", false), SIX(6, "韩", true);

    //定义都有哪些属性，可以比枚举中的属性少
    private Integer retCode;
    private String retMsg;
    private boolean active;

    //初始化，及规定属性的相对位置，构造方法中规定了枚举的属性和顺序
    CountryEnum(Integer retCode, String retMsg, boolean active) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.active = active;
    }

    //一个取值的案例，可以是其他的值，这个是用于传一个retCode过来获取对应的值，
    // 如果能确定哪个枚举的话，也可以CountryEnum.TWO.getRetMsg()获取值
    public static CountryEnum list(int idx) {
        //获取所有枚举属性
        CountryEnum[] countryEnums = CountryEnum.values();
//        遍历比较retCode
        for (CountryEnum countryEnum : countryEnums) {
            if (idx == countryEnum.getRetCode())
                return countryEnum;
        }
        return null;
    }
}
