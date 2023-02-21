package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.strategy.DirectShoot;
import edu.hitsz.strategy.ShootStrategy;

import java.util.LinkedList;
import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /** 单例模式 */
    private volatile static HeroAircraft heroAircraft;

    /** 攻击方式 */
//    private int shootNum = 1;     //子弹一次发射数量
//    private int power = 30;       //子弹伤害
//    private int direction = -1;  //子弹射击方向 (向上发射：1，向下发射：-1)

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int direction, int shootNum, int power, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp, direction, shootNum, power, shootStrategy);
    }

    /**利用双重检查锁定实现单例模式，获取唯一英雄机实例的静态方法*/
    public static HeroAircraft getHeroAircraft(){
        if(heroAircraft == null){
            synchronized (HeroAircraft.class){
                if(heroAircraft == null){
                    heroAircraft = new HeroAircraft(
                            Main.WINDOW_WIDTH / 2,
                            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 1000, -1, 1, 50, new DirectShoot());
                }
            }
        }
        return heroAircraft;
    }

    public void increaseHp(int increase){
        hp += increase;
        if(hp > maxHp) {
            hp = maxHp;
        }
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }


}
