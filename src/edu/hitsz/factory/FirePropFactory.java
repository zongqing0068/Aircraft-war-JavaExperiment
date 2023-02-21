package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.FireSupplyProp;

/*火力道具工厂*/
public class FirePropFactory extends AbstractPropFactory{

    /*创建火力道具实例*/
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new FireSupplyProp(locationX, locationY, speedX, speedY);
    }
}
