package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/*加血道具*/
public class BloodSupplyProp extends AbstractProp{
    public BloodSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /*加血道具生效后英雄机血量增加，同时道具消失*/
    @Override
    public void function(HeroAircraft heroAircraft){
        heroAircraft.increaseHp(10);
        this.vanish();
    }
}
