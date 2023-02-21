package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;

/*普通敌机工厂*/
public class MobEnemyFactory extends AbstractEnemyFactory {

    private static int speedY = 10;
    private static int hp = 30;

    /*创建普通敌机实例*/
    @Override
    public AbstractEnemyAircraft createEnemy() {

        int locationX = (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()) + ImageManager.MOB_ENEMY_IMAGE.getWidth() * 0.5);
        int locationY = (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2);
        int speedX = 0;

        /*返回产生的普通敌机*/
        return new MobEnemy(locationX, locationY, speedX, speedY, hp);
    }

    public static void changeAttribute(double diffRate){
        int hpOriginal = 30;
        int speedYOriginal = 10;
        hp = (int)(hpOriginal * diffRate);
        speedY = (int)(speedYOriginal * diffRate);
        System.out.printf("普通敌机血量：%d，普通敌机速度：%d，", hp, speedY);
    }
}
