package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/*
 * 子弹散射策略
 */
public class ScatteringShoot implements ShootStrategy {
    @Override
    public List<BaseBullet> doShoot(AbstractAircraft aircraft) {

        List<BaseBullet> res = new LinkedList<>();

        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection()*2;
        int speedY = aircraft.getSpeedY() + aircraft.getDirection()*5;
        BaseBullet abstractBullet;
        for(int i=0; i<aircraft.getShootNum(); i++){

            /*散射，所以x轴速度根据子弹数量均匀分布*/
            int speedX = -(aircraft.getShootNum()-1)/2;

            if(aircraft instanceof HeroAircraft){
                abstractBullet = new HeroBullet(x + (i*2 - aircraft.getShootNum() + 1)*10, y, speedX+i, speedY, aircraft.getPower());
            }
            else {
                abstractBullet = new EnemyBullet(x + (i*2 - aircraft.getShootNum() + 1)*10, y, speedX+i, speedY, aircraft.getPower());
            }
            res.add(abstractBullet);
        }
        return res;
    }
}
