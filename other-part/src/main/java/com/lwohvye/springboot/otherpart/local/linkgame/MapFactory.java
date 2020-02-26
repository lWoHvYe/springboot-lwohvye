package com.lwohvye.springboot.otherpart.local.linkgame;

/**
 * @author Hongyan Wang
 * @description 创建n*n空白底图，即存放图片的map
 * @date 2020/2/23 16:06
 */
public class MapFactory {

    static int[][] map;

    public MapFactory() {

    }

    public static int[][] getMap(int n) {
        map = new int[n][n];//生成n*n地图

        //初始化地图信息为空
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                map[i][j] = -1;
            }
        }

        return map;
    }

}
