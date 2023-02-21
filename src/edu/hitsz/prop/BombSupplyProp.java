package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.AbstractGame;
import edu.hitsz.application.MusicThread;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.observer.Subscriber;

import java.util.ArrayList;
import java.util.List;

/*炸弹道具*/
public class BombSupplyProp extends AbstractProp{

    public BombSupplyProp(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    /*观察者列表*/
    private List<Subscriber> subscriberList = new ArrayList<>();

    /*增加观察者*/
    public void addSubscriber(Subscriber subscriber){
        subscriberList.add(subscriber);
    }

    /*删除观察者*/
    public void removeSubscriber(Subscriber subscriber){
        subscriberList.remove(subscriber);
    }

    /*通知所有观察者*/
    public void notifyAllSubscribers(){
        for (Subscriber subscriber : subscriberList){
            subscriber.update();
        }
    }

    /*炸弹道具生效后清除界面上除boss机外的所有敌机和敌机子弹，同时产生相应音效、道具消失*/
    @Override
    public void function(HeroAircraft heroAircraft){
        MusicThread bombMusic = new MusicThread("src/videos/bomb_explosion.wav");
        bombMusic.start();
        notifyAllSubscribers();
        this.vanish();
        System.out.println("BombSupply active!");
    }
}
