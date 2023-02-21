package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombSupplyProp;

/*炸弹道具工厂*/
public class BombPropFactory extends AbstractPropFactory{

    /*创建炸弹道具实例*/
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BombSupplyProp(locationX, locationY, speedX, speedY);
    }
}
