package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.AbstractPropFactory;
import edu.hitsz.factory.BloodPropFactory;
import edu.hitsz.factory.BombPropFactory;
import edu.hitsz.factory.FirePropFactory;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.strategy.ShootStrategy;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * boss敌机
 * 可射击
 * 可产生道具
 */
public class BossEnemy extends AbstractEnemyAircraft{

    /** 攻击方式 */
//    private int shootNum = 1;     //子弹一次发射数量
//    private int power = 30;       //子弹伤害
//    private int direction = 1;  //子弹射击方向 (向上发射：1，向下发射：-1)

    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int direction, int shootNum, int power, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp, direction, shootNum, power, shootStrategy);
    }



    /*
        boss敌机可按概率产生三种道具之一，或者不产生道具
     */
    @Override
    public AbstractProp produceProp(){

        AbstractPropFactory propFactory;

        Random random = new Random();
        int choice = random.nextInt(4);
        switch (choice){
            case 1:
                propFactory = new BloodPropFactory();
                return propFactory.createProp(this.getLocationX(), this.getLocationY(), 0, 5);
            case 2:
                propFactory = new BombPropFactory();
                return propFactory.createProp(this.getLocationX(), this.getLocationY(), 0, 5);
            case 3:
                propFactory = new FirePropFactory();
                return propFactory.createProp(this.getLocationX(), this.getLocationY(), 0, 5);
            default: return null;
        }
    }
}
