package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.DirectShoot;

import java.util.Random;

/*精英敌机工厂*/
public class EliteEnemyFactory extends AbstractEnemyFactory {

    private static int speedY = 10;
    private static int hp = 60;


    /*创建精英敌机实例*/
    @Override
    public AbstractEnemyAircraft createEnemy() {
        int shootNum = 1;     //子弹一次发射数量
        int power = 30;       //子弹伤害
        int direction = 1;  //子弹射击方向 (向上发射：1，向下发射：-1)
        int locationX = (int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth()));
        int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2);

        /*精英敌机以50%的概率向左或向右飞*/
        Random random = new Random();
        int aircraftDirection = random.nextInt(2);
        int speedX;
        if(aircraftDirection == 0){
            speedX = 2;
        }
        else{
            speedX = -2;
        }

        /*返回产生的精英敌机*/
        return new EliteEnemy(locationX, locationY, speedX, speedY, hp, direction, shootNum, power, new DirectShoot());
    }

    public static void changeAttribute(double diffRate){
        int hpOriginal = 60;
        int speedYOriginal = 10;
        hp = (int)(hpOriginal * diffRate);
        speedY = (int)(speedYOriginal * diffRate);
        System.out.printf("精英敌机血量：%d，精英敌机速度：%d\n", hp, speedY);
    }

}
