package edu.hitsz.application;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.factory.BossEnemyFactory;
import edu.hitsz.factory.EliteEnemyFactory;
import edu.hitsz.factory.MobEnemyFactory;

import java.awt.*;

import static java.lang.Math.min;

public class HardGame extends AbstractGame {

    private double diffRate = 1;

    public HardGame(){
        enemyMaxNumber = 9;
        cycleDuration = 400;
        bossScoreThreshold = 200;
        enemyRate = 0.5;
    }

    /**按照得分阈值周期性产生boss敌机（血量逐次增加），同时播放boss敌机出场音乐*/
    @Override
    protected void createBossAction() {
        if (newCycleJudge()){
            int haveBoss = 0;
            for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts){
                if(enemyAircraft instanceof BossEnemy){
                    haveBoss = 1;
                    break;
                }
            }
            if (getScore() >= bossScoreThreshold && haveBoss == 0){
                enemyFactory = new BossEnemyFactory();
                BossEnemy bossEnemy = (BossEnemy) enemyFactory.createEnemy();

                /*每次召唤提升boss敌机的血量*/
                bossEnemy.setHp(bossEnemy.getHp() + 200*bossNum);
                enemyAircrafts.add(bossEnemy);
                bossScoreThreshold += bossScoreThreshold; //下一次boss敌机出现的阈值
                bossNum += 1; //boss敌机出现的次数加一
                System.out.println("当前产生的boss敌机的血量为"+bossEnemy.getHp());

                /*播放boss敌机出场音乐*/
                bossOnScreen = true;
                bgmBossMusic = new MusicThread("src/videos/bgm_boss.wav");
                bgmBossMusic.start();
            }
        }
    }

    /**周期性改变难度，包括精英敌机比例、各类敌机产生周期、各类敌机血量与速度、各类飞机射击周期等*/
    @Override
    protected void changeDiffAction() {
        if(diffChangeJudge()){
            double diffRateMax = 1.5;
            if(diffRate < diffRateMax){
                enemyRate += 0.01;
                cycleDuration -= 10;
                System.out.printf("提高难度！精英机概率：%.2f，敌机周期：%.2f，敌机属性提升倍率：%.2f，", enemyRate, (double)cycleDuration/timeInterval, diffRate);
                MobEnemyFactory.changeAttribute(diffRate);
                EliteEnemyFactory.changeAttribute(diffRate);
                diffRate += 0.1;
            }
            else {
                System.out.print("难度已提升到最大！\n");
            }
        }
    }

    @Override
    protected void paintBackground(Graphics g) {
        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_Hard_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_Hard_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }
    }
}
