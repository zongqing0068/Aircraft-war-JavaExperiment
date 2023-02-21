package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.strategy.ShootStrategy;

/*
 *  所有敌机的抽象父类
 *  敌机（BOSS, ELITE, MOB）
 */

public abstract class AbstractEnemyAircraft extends AbstractAircraft{

    /**可以发射子弹的敌机的构造函数*/
    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int direction, int shootNum, int power, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp, direction, shootNum, power, shootStrategy);
    }

    /**不能发射子弹的敌机的构造函数*/
    public AbstractEnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }


    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    /*
     *  敌机产生道具的抽象方法，可产生道具对象必须实现
     *   @return
     *  可产生道具对象需实现，按概率返回产生的不同种类道具或null
     *  非可产生道具对象空实现，返回null
     */
    public abstract AbstractProp produceProp();

    public void setLocation(int locationX, int locationY){
        this.locationX = locationX;
        this.locationY = locationY;
    }

}
