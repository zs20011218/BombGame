package com.zjr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 程序运行类
 * JFrame - 监听键盘
 * @author user
 * @date 2023/05/10
 */
public class GameWin extends JFrame {

    public static void main(String[] args) {
        GameWin gameWin = new GameWin();
        gameWin.launch();
    }

    /**
     * 宽度
     */
    int wigth = 2 * GameUtil.OFFSET + GameUtil.MAP_W * GameUtil.SQUARE_LENGTH;
    /**
     * 高
     */
    int height = 4* GameUtil.OFFSET + GameUtil.MAP_H * GameUtil.SQUARE_LENGTH;

    /**
     * 背景墙 防止闪屏
     */
    Image offScreenImage = null;

    /**
     *
     */
    MapBottom mapBotton = new MapBottom();
    /**
     * 绘制地图顶层
     */
    MapTop mapTop = new MapTop();
    /**
     * 创建窗口
     */
    void launch() {
        // 创建窗口
        this.setVisible(true); //窗口可见
        this.setSize(wigth,height); //窗口大小
        this.setLocationRelativeTo(null); //窗口位置 - 剧中
        this.setTitle("张靖茹 - 扫雷游戏");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 关闭窗口方法

        //鼠标事件 -- 采集鼠标左键和右键
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //先判断游戏状态
                switch (GameUtil.state){
                    // 进行中
                    case 0 :
                        // 左键是1
                        if(e.getButton()==1){
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            GameUtil.LEFT = true;
                        }
                        //右键是3
                        if(e.getButton()==3) {
                            GameUtil.MOUSE_X = e.getX();
                            GameUtil.MOUSE_Y = e.getY();
                            GameUtil.RIGHT = true;
                        }
                    //胜利 和 失败
                    case 1 :
                    case 2 :
                        //把游戏重置到进行中
                        if(e.getButton()==1){
                            if(e.getX()>GameUtil.OFFSET + GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2)
                                    && e.getX()<GameUtil.OFFSET + GameUtil.SQUARE_LENGTH*(GameUtil.MAP_W/2) + GameUtil.SQUARE_LENGTH
                                    && e.getY()>GameUtil.OFFSET
                                    && e.getY()<GameUtil.OFFSET+GameUtil.SQUARE_LENGTH){
                                mapBotton.reGame();
                                mapTop.reGame();

                                GameUtil.state=0;
                            }
                        }
                        break;
                    default:

                }

            }
        });

        //每隔四十毫秒刷新一下画面
        while (true){
            // 重新刷新一下画面 调用下面的paint
            repaint();
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //绘制雷区
    @Override
    public void paint(Graphics g) {
        //解决双层闪动问题
        offScreenImage = this.createImage(wigth,height);
        Graphics gImage = offScreenImage.getGraphics();

        //设置背景颜色
        gImage.setColor(Color.orange);
        gImage.fillRect(0,0,wigth,height);

        // 绘制地图底层和顶层调用
        mapBotton.paintSelf(gImage);
        mapTop.paintSelf(gImage);

        g.drawImage(offScreenImage,0,0,null);
    }


}
