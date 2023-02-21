package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;

/*
 *  所有道具工厂的抽象父类
 *  道具工厂（BLOOD, BOMB, FIRE）
 */

public abstract class AbstractPropFactory {
    public abstract AbstractProp createProp(int locationX,int locationY,int speedX,int speedY);
}
