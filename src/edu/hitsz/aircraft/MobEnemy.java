package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.observer.Subscriber;
import edu.hitsz.prop.AbstractProp;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通敌机
 * 不可射击
 */
public class MobEnemy extends AbstractEnemyAircraft implements Subscriber {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

//    @Override
//    public List<BaseBullet> shoot() {
//        return new LinkedList<>();
//    }

    @Override
    public AbstractProp produceProp(){
        return null;
    } //普通敌机不产生道具

    /*普通敌机在炸弹生效时消失*/
    @Override
    public void update() {
        this.vanish();
    }
}
