package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.strategy.ShootStrategy;

import java.util.List;

/**
 * 所有种类飞机的抽象父类：
 * 敌机（BOSS, ELITE, MOB），英雄飞机
 *
 * @author hitsz
 */
public abstract class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    protected int direction;
    protected int shootNum;
    protected int power;
    protected ShootStrategy shootStrategy;

    /**无子弹的飞机(如MobEnemy)的构造函数*/
    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
    }

    /**有子弹的飞机(如Hero等)的构造函数*/
    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int direction, int shootNum, int power, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.direction = direction;
        this.shootNum = shootNum;
        this.power = power;
        this.shootStrategy = shootStrategy;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public void setHp(int hp){this.hp = hp;}

    public int getHp() {return hp;}

    public int getDirection() {return direction;}

    public void setShootNum(int shootNum){this.shootNum = shootNum;}

    public int getShootNum() {return shootNum;}

    public int getPower() {return power;}

    public void setShootStrategy(ShootStrategy shootStrategy){this.shootStrategy = shootStrategy;}

    /**
     * 飞机射击方法，调用实际策略相对应的子弹发射方法
     * @return
     * 返回子弹列表
     * 若没有发射子弹功能（即没有设置子弹发射方式）的飞机调用该方法则会报错
     */
    public List<BaseBullet> executeShoot(){return shootStrategy.doShoot(this);}

}


