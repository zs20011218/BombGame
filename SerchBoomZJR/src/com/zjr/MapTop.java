package com.zjr;

import org.ietf.jgss.GSSManager;

import java.awt.*;

/**
 * 顶层地图类
 * 绘制顶层组件
 * 判断逻辑
 *
 * @author user
 * @date 2023/05/11
 */
public class MapTop {

    //格子位置
    int temp_x;
    int temp_y;

    /**
     * 重新游戏
     */
    void reGame(){
        for (int i = 1; i <=GameUtil.MAP_W ; i++) {
            for (int j = 1; j <=GameUtil.MAP_H ; j++) {
                GameUtil.DATA_TOP[i][j]=0;
            }
        }

    }
    void logic() {
        //鼠标定位
        temp_x=0;
        temp_y=0;
        //if判断点击区域是否超过雷盘 ， 然后定位x y点的哪一块
        if(GameUtil.MOUSE_X>GameUtil.OFFSET && GameUtil.MOUSE_Y>3*GameUtil.OFFSET){
            temp_x = (GameUtil.MOUSE_X - GameUtil.OFFSET)/GameUtil.SQUARE_LENGTH+1;
            temp_y = (GameUtil.MOUSE_Y - GameUtil.OFFSET * 3)/GameUtil.SQUARE_LENGTH+1;
        }

        //if 判断上面的xy是不是在雷盘里面，如果不是就跳过了
        if(temp_x>=1 && temp_x<=GameUtil.MAP_W
                && temp_y>=1 && temp_y<=GameUtil.MAP_H){

            //这个是左键的操作 - 翻牌子，把牌子位置标记为-1，牌子就消失了
            if(GameUtil.LEFT){
                //如果牌子的状态是覆盖（0）,则翻开（-1）
                if(GameUtil.DATA_TOP[temp_x][temp_y]==0){
                    GameUtil.DATA_TOP[temp_x][temp_y]=-1;
                }
                //如果牌子底层的数字是0 - 就把周边的牌子翻开 - 通过递归调用实现
                spaceOpen(temp_x,temp_y);
                //把鼠标左键状态改为取消
                GameUtil.LEFT=false;
            }

            //右键的事件
            if (GameUtil.RIGHT){
                //覆盖就插旗
                if(GameUtil.DATA_TOP[temp_x][temp_y]==0){
                    GameUtil.DATA_TOP[temp_x][temp_y]=1;
                }
                //插旗就取消
                else if(GameUtil.DATA_TOP[temp_x][temp_y]==1){
                    GameUtil.DATA_TOP[temp_x][temp_y]=0;
                }
                //如果数字周边的旗子数量符合数字数量，就把数字周边的其他格子掀开
                else if(GameUtil.DATA_TOP[temp_x][temp_y] == -1){
                    numOpen(temp_x,temp_y);
                }
                //把右键的事件改为否
                GameUtil.RIGHT = false;
            }
        }
        //判断是否失败
        boom();

        //判断是否胜利
        victory();
    }

    //失败判定  t 表示失败 f 未失败
    boolean boom(){
        for (int i = 1; i <=GameUtil.MAP_W ; i++) {
            for (int j = 1; j <=GameUtil.MAP_H ; j++) {
                if(GameUtil.DATA_BOTTOM[i][j]==-1&&GameUtil.DATA_TOP[i][j]==-1){
                    GameUtil.state = 2;
                    seeBoom();
                    return true;
                }
            }
        }
        return false;
    }

    //失败显示
    void seeBoom(){
        for (int i = 1; i <=GameUtil.MAP_W ; i++) {
            for (int j = 1; j <=GameUtil.MAP_H ; j++) {
                //底层是雷,顶层不是旗,显示
                if(GameUtil.DATA_BOTTOM[i][j]==-1&&GameUtil.DATA_TOP[i][j]!=1){
                    GameUtil.DATA_TOP[i][j]=-1;
                }
                //底层不是雷,顶层是旗,显示差错旗
                if(GameUtil.DATA_BOTTOM[i][j]!=-1&&GameUtil.DATA_TOP[i][j]==1){
                    GameUtil.DATA_TOP[i][j]=2;
                }

                //底层不是雷
                if(GameUtil.DATA_BOTTOM[i][j]!=-1&&GameUtil.DATA_TOP[i][j]==0){
                    GameUtil.DATA_TOP[i][j]=-1;
                }


            }
        }
    }

    //胜利判断  t 表示胜利 f 未胜利
    boolean victory(){
        //统计未打开格子数
        int count=0;
        for (int i = 1; i <=GameUtil.MAP_W ; i++) {
            for (int j = 1; j <=GameUtil.MAP_H ; j++) {
                if(GameUtil.DATA_TOP[i][j]!=-1){
                    count++;
                }
            }
        }
        if(count==GameUtil.RAY_MAX){
            GameUtil.state=1;
            for (int i = 1; i <=GameUtil.MAP_W ; i++) {
                for (int j = 1; j <=GameUtil.MAP_H ; j++) {
                    //未翻开,变成旗
                    if(GameUtil.DATA_TOP[i][j]==0){
                        GameUtil.DATA_TOP[i][j]=1;
                    }
                }
            }
            return true;
        }
        return false;
    }
    //数字翻开
    void numOpen(int x,int y){
        //记录旗数
        int count=0;
        if(GameUtil.DATA_BOTTOM[x][y]>0){
            for (int i = x-1; i <=x+1 ; i++) {
                for (int j = y-1; j <=y+1 ; j++) {
                    if(GameUtil.DATA_TOP[i][j]==1){
                        count++;
                    }
                }
            }
            if(count==GameUtil.DATA_BOTTOM[x][y]){
                for (int i = x-1; i <=x+1 ; i++) {
                    for (int j = y-1; j <=y+1 ; j++) {
                        if(GameUtil.DATA_TOP[i][j]!=1){
                            GameUtil.DATA_TOP[i][j]=-1;
                        }
                        //必须在雷区当中
                        if(i>=1&&j>=1&&i<=GameUtil.MAP_W&&j<=GameUtil.MAP_H){
                            spaceOpen(i,j);
                        }
                    }
                }
            }
        }
    }

    //打开空格
    void spaceOpen(int x,int y){
        if(GameUtil.DATA_BOTTOM[x][y]==0){
            for (int i = x-1; i <=x+1 ; i++) {
                for (int j = y-1; j <=y+1 ; j++) {
                    //覆盖,才递归
                    if(GameUtil.DATA_TOP[i][j]!=-1){
                        //if(GameUtil.DATA_TOP[i][j]==1){GameUtil.FLAG_NUM--;}
                        GameUtil.DATA_TOP[i][j]=-1;
                        //必须在雷区当中
                        if(i>=1&&j>=1&&i<=GameUtil.MAP_W&&j<=GameUtil.MAP_H){
                            spaceOpen(i,j);
                        }
                    }
                }
            }
        }
    }

    //绘制
    void paintSelf(Graphics g){
        logic();
        for (int i = 1; i <= GameUtil.MAP_W ; i++) {
            for (int j = 1; j <= GameUtil.MAP_H; j++) {
                //覆盖
                if (GameUtil.DATA_TOP[i][j] == 0) {
                    g.drawImage(GameUtil.top,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
                //插旗
                if (GameUtil.DATA_TOP[i][j] == 1) {
                    g.drawImage(GameUtil.flag,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
                //差错旗
                if (GameUtil.DATA_TOP[i][j] == 2) {
                    g.drawImage(GameUtil.noflag,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }


            }
        }
    }

}
