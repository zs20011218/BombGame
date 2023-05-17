package com.zjr;

/**
 * 初始化地雷
 *
 * @author user
 * @date 2023/05/10
 */
public class BottomRay {

    //存放所有地雷坐标
    int[] rays = new int[GameUtil.RAY_MAX*2];

    //地雷坐标
    int x,y;

    //参数 - 是否可以放
    boolean isPlace = true;
    void newRay(){
        for (int i = 0; i < GameUtil.RAY_MAX*2 ; i=i+2) {
            // 随机生成 地雷的 x 和 y
            x= (int) (Math.random()*GameUtil.MAP_W +1);//1-12
            y= (int) (Math.random()*GameUtil.MAP_H +1);//1-12

            //判断坐标是否存在
            for (int j = 0; j < i ; j=j+2) {
                if(x==rays[j] && y==rays[j+1]){
                    i=i-2;
                    isPlace = false;
                    break;
                }
            }

            //将坐标放入数组
            if(isPlace){
                rays[i]=x;
                rays[i+1]=y;
            }
            isPlace = true;
        }

        for (int i = 0; i < GameUtil.RAY_MAX*2; i=i+2) {
            //ray[i] 存放的是雷的x坐标， ray[i+1] 存放的是雷的y坐标 -1就是雷
            GameUtil.DATA_BOTTOM[rays[i]][rays[i+1]]=-1;
        }
    }
}
