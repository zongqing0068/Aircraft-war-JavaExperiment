package edu.hitsz.application;

import java.awt.*;

public class EasyGame extends AbstractGame {

    public EasyGame(){
        enemyMaxNumber = 3;
        cycleDuration = 600;
        enemyRate = 0.3;
    }

    /**简单模式不产生boss敌机，产生boss敌机函数为空*/
    @Override
    protected void createBossAction() {}

    /**简单模式不改变难度，改变难度函数为空*/
    @Override
    protected void changeDiffAction() {}

    /**设置简单模式背景图*/
    @Override
    protected void paintBackground(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_Easy_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_Easy_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }
    }
}
