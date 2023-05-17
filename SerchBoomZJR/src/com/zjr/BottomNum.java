package com.zjr;

/**
 * 底层数字类
 *
 * @author user
 * @date 2023/05/10
 */
public class BottomNum {
    void newNum(){
        //直接初始化数字 在雷区周边额外增加一圈避免数组越界，然后每个雷周边的非雷区+1
        for(int i = 1 ; i <= GameUtil.MAP_W ; i++) {
            for (int j = 1 ; j<= GameUtil.MAP_H ; j++) {
                //看看是不是雷
                if (GameUtil.DATA_BOTTOM[i][j] == -1) {
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            if (GameUtil.DATA_BOTTOM[k][l] >= 0) {
                                GameUtil.DATA_BOTTOM[k][l]++;
                            }
                        }

                    }
                }
            }
        }

    }

}
