package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractEnemyAircraft;

/*
 *  所有敌机工厂的抽象父类
 *  敌机工厂（BOSS, ELITE, MOB）
 */

public abstract class AbstractEnemyFactory {

    /*
     *  抽象敌机工厂创建敌机实例的抽象方法，敌机工厂对象必须实现
     *   @return 各种类的敌机实例
     */
    public abstract AbstractEnemyAircraft createEnemy();

}
