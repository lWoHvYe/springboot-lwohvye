package com.springboot.shiro.shiro2spboot.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 时间相关工具类
 */
public class DateTimeUtil {

    /**
     * 获取当前格式化日期时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurFormatTime() {
//      获取本时区当前时间
        ZonedDateTime now = Instant.now().atZone(ZoneId.of("Asia/Shanghai"));
//      获取格式化时间
        LocalDateTime localDateTime = now.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = localDateTime.format(formatter);
        return format;
    }

    /**
     * 获取当前时间毫秒值
     *
     * @return
     */
    public static long getCurMilli() {
        long milli = LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        return milli;
    }

    /**
     * 获取对应天前凌晨对应的毫秒值
     *
     * @param days
     * @return
     */
    public static long minusDayMin(int days) {
        long milli = LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.MIN).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        return milli;
    }

    /**
     * 获取对应天前深夜对应的毫秒值
     * @param days
     * @return
     */
    public static long minusDayMax(int days){
        long milli = LocalDateTime.of(LocalDate.now().minusDays(days), LocalTime.MAX).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        return milli;
    }

    /**
     * 获取对应小时前对应的毫秒值
     *
     * @param hours
     * @return
     */
    public static long minusHoursMilli(int hours) {
        long milli = LocalDateTime.now().minusHours(hours).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        return milli;
    }

    /**
     * 获取对应分钟前对应的毫秒值
     * @param minutes
     * @return
     */
    public static long minusMinutesMilli(int minutes){
        long milli = LocalDateTime.now().minusMinutes(minutes).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        return milli;
    }

    /**
     * 根据毫秒值获取对应格式化时间
     * @param milli
     * @return
     */
    public static String formatMillis(long milli){
        Instant instant = Instant.ofEpochMilli(milli);
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = localDateTime.format(formatter);
        return format;
    }
}
