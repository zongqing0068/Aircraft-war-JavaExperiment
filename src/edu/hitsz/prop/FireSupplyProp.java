package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.AbstractGame;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.strategy.DirectShoot;
import edu.hitsz.strategy.ScatteringShoot;

import java.util.List;

import static java.lang.Math.min;

/*火力道具*/
public class FireSupplyProp extends AbstractProp{
    public FireSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /*火力道具生效后，一段时间内英雄机改变弹道，同时道具消失*/
    @Override
    public void function(HeroAircraft heroAircraft) {
        //火力道具可叠加(即射出子弹数量增加)，但最多同时射出3个
        Runnable r = ()->{
            try {
                heroAircraft.setShootNum(min(heroAircraft.getShootNum()+1,3));
                heroAircraft.setShootStrategy(new ScatteringShoot());
                System.out.println("FireSupply active!");
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                AbstractGame.firePropNum -= 1;
                /*只有火力道具全部失效时才会改回直射*/
                if(AbstractGame.firePropNum == 0){
                    heroAircraft.setShootNum(1);
                    heroAircraft.setShootStrategy(new DirectShoot());
                    System.out.println("FireSupply end!");
                }
            }
        };

        this.vanish();
        AbstractGame.firePropNum += 1;
        new Thread(r).start();

    }
}
