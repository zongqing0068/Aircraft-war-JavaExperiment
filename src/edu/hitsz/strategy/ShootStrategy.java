package edu.hitsz.strategy;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.bullet.BaseBullet;

import java.util.List;

/*射击策略接口*/
public interface ShootStrategy {
    List<BaseBullet> doShoot(AbstractAircraft aircraft);
}
