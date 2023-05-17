package com.zjr;


import java.awt.*;

/**
 * 地图绘制
 *
 * @author user
 * @date 2023/05/10
 */
public class MapBottom {

    BottomRay bottomRay = new BottomRay();
    BottomNum bottomNum = new BottomNum();

    // 初始化雷区
    {
        bottomRay.newRay();
        bottomNum.newNum();
    }

    /**
     * 重新游戏
     */
    void reGame(){
        for (int i = 1; i <=GameUtil.MAP_W ; i++) {
            for (int j = 1; j <=GameUtil.MAP_H ; j++) {
                GameUtil.DATA_BOTTOM[i][j]=0;
            }
        }
        bottomRay.newRay();
        bottomNum.newNum();
    }
    /**
     * 绘制方法
     */
    void paintSelf(Graphics g) {
        g.setColor(Color.red);
        //画横线
        for (int i = 0 ; i <= GameUtil.MAP_W ; i++) {
            g.drawLine(
                    GameUtil.OFFSET + i*GameUtil.SQUARE_LENGTH,
                    3*GameUtil.OFFSET,
                    GameUtil.OFFSET + i*GameUtil.SQUARE_LENGTH,
                    3*GameUtil.OFFSET+GameUtil.MAP_H*GameUtil.SQUARE_LENGTH
            );
        }
        //画竖线
        for (int i = 0 ; i <= GameUtil.MAP_W ; i++) {
            g.drawLine(
                    GameUtil.OFFSET,
                    3*GameUtil.OFFSET + i*GameUtil.SQUARE_LENGTH,
                    GameUtil.OFFSET+GameUtil.MAP_W*GameUtil.SQUARE_LENGTH,
                    3*GameUtil.OFFSET + i*GameUtil.SQUARE_LENGTH
            );
        }

        //放地雷
        for (int i = 1; i <= GameUtil.MAP_W ; i++){
            for (int j = 1 ; j <= GameUtil.MAP_H ; j++) {
                if (GameUtil.DATA_BOTTOM[i][j] == -1) { //判断是不是随机生成的地雷
                    //放地雷
                    g.drawImage(GameUtil.lei,
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }

                if (GameUtil.DATA_BOTTOM[i][j] >=0) { //判断不是地雷的
                    //放数字
                    g.drawImage(GameUtil.images[GameUtil.DATA_BOTTOM[i][j]],
                            GameUtil.OFFSET + (i - 1) * GameUtil.SQUARE_LENGTH,
                            GameUtil.OFFSET * 3 + (j - 1) * GameUtil.SQUARE_LENGTH + 1,
                            GameUtil.SQUARE_LENGTH - 2,
                            GameUtil.SQUARE_LENGTH - 2,
                            null);
                }
            }
        }

        //判断游戏状态 0：进行中 ， 1：胜利 ， 2：失败 通过不同游戏状态显示头顶图片
        switch (GameUtil.state){
            case 0:
                //GameUtil.END_TIME=System.currentTimeMillis();
                g.drawImage(GameUtil.face,
                        GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W/2),
                        GameUtil.OFFSET,
                        null);
                break;
            case 1:
                g.drawImage(GameUtil.win,
                        GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W/2),
                        GameUtil.OFFSET,
                        null);
                break;
            case 2:
                g.drawImage(GameUtil.over,
                        GameUtil.OFFSET + GameUtil.SQUARE_LENGTH * (GameUtil.MAP_W/2),
                        GameUtil.OFFSET,
                        null);
                break;

            default:
        }

    }
}
