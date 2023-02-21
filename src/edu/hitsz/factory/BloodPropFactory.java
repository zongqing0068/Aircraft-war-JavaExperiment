package edu.hitsz.factory;

import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodSupplyProp;

/*加血道具工厂*/
public class BloodPropFactory extends AbstractPropFactory{

    /*创建加血道具实例*/
    @Override
    public AbstractProp createProp(int locationX, int locationY, int speedX, int speedY) {
        return new BloodSupplyProp(locationX, locationY, speedX, speedY);
    }
}
