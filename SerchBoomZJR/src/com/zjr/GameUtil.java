package com.zjr;

import java.awt.*;

/**
 * 工具类
 * 存放静态参数
 * 工具方法
 * @author user
 * @date 2023/05/10
 */
public class GameUtil {

    /**
     * 游戏状态 0：游戏中 1：胜利 2：失败
     */
    static int state = 0;

    /**
     * 地雷个数
     */
    static int RAY_MAX = 10;
    /**
     * 地图宽
     */
    static int MAP_W = 11;
    /**
     * 地图高
     */
    static int MAP_H = 11;
    /**
     * 雷区偏移量
     */
    static int OFFSET = 45;
    /**
     * 格子边长
     */
    static int SQUARE_LENGTH = 50;
    /**
     * 鼠标横坐标
     */
    static int MOUSE_X;
    /**
     * 鼠标纵坐标
     */
    static int MOUSE_Y;

    //鼠标状态
    static boolean LEFT = false;

    static boolean RIGHT = false;

    /**
     * 底层元素 -1 雷 0 空1-8 表示对应数字  --     +2 操作是解决数组越界问题的方法
     */
    static int[][] DATA_BOTTOM = new int[MAP_W+2][MAP_H+2];

    /**
     * 顶层元素 -1：无覆盖 0：覆盖 1：插旗 2：插错旗
     */
    static int[][] DATA_TOP = new int[MAP_W+2][MAP_H+2];
    //载入图片
    static Image lei = Toolkit.getDefaultToolkit().getImage("imgs/lei.png");
    static Image top = Toolkit.getDefaultToolkit().getImage("imgs/top.gif");
    static Image flag = Toolkit.getDefaultToolkit().getImage("imgs/flag.gif");
    static Image noflag = Toolkit.getDefaultToolkit().getImage("imgs/noflag.png");
    static Image win = Toolkit.getDefaultToolkit().getImage("imgs/num/1.png");
    static Image over = Toolkit.getDefaultToolkit().getImage("imgs/num/2.png");
    static Image face = Toolkit.getDefaultToolkit().getImage("imgs/num/0.png");
    static Image[] images = new Image[9];
    static {
        for (int i = 0; i< 9 ; i++) {
            images[i] = Toolkit.getDefaultToolkit().getImage("imgs/num/" + i + ".png");
        }
    }
}
