package edu.hitsz.bullet;

import edu.hitsz.observer.Subscriber;

/**
 * @Author hitsz
 */
public class EnemyBullet extends BaseBullet implements Subscriber {

    public EnemyBullet(int locationX, int locationY, int speedX, int speedY, int power) {
        super(locationX, locationY, speedX, speedY, power);
    }

    /*敌机子弹在炸弹生效时消失*/
    @Override
    public void update() {
        this.vanish();
    }
}
