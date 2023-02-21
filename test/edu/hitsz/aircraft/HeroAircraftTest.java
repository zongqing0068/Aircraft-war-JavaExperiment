package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.*;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HeroAircraftTest {

    private HeroAircraft heroAircraft;

    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        heroAircraft = HeroAircraft.getHeroAircraft();
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
        heroAircraft = null;
    }

    @Test
    @DisplayName("Test decreaseHp method")
    //测试英雄机类（抽象飞机类）的减血功能是否正常
    void testDecreaseHp() {
        System.out.println("**--- This is testDecreaseHp ---**");
        int decrease = 10;
        int maxHp = heroAircraft.getHp();
        for(int i=1; i<=12; i++){
            heroAircraft.decreaseHp(decrease);
            //当所减血量大于已有血量时，血量降为0（即血量不会为负数）
            assertEquals(heroAircraft.getHp(), max(maxHp-i*decrease, 0));
            //血量为0时，英雄机坠毁（失效）
            if(heroAircraft.getHp() == 0){
                assertTrue(heroAircraft.notValid());
            }
        }
    }

    @Test
    @DisplayName("Test increaseHp method")
    //测试英雄机类的加血功能是否正常
    void testIncreaseHp() {
        System.out.println("**--- This is testIncreaseHp ---**");
        int maxHp = heroAircraft.getHp();//最大血量
        int origHp = 70;//开始测试时的当前血量
        heroAircraft.setHp(origHp);//在抽象飞机类中增加了setHp方法
        int increase = 10;
        for(int i=1; i<=4; i++){
            heroAircraft.increaseHp(increase);
            //当血量大于最大血量时，血量保持在最大值
            assertEquals(heroAircraft.getHp(), min(origHp+i*increase, maxHp));
        }
    }

    @Test
    @DisplayName("Test shoot method")
    //测试英雄机类的射击功能是否正常
    void testShoot() {
        System.out.println("**--- This is testShoot ---**");
        List<BaseBullet> res = heroAircraft.executeShoot();
        assertNotNull(res);//成功射出子弹
        for(BaseBullet bullet: res){
            assertEquals(bullet.getLocationX(), heroAircraft.getLocationX());
            assertEquals(bullet.getLocationY(), heroAircraft.getLocationY()-2);
            assertEquals(bullet.getSpeedX(), 0);
            assertEquals(bullet.getSpeedY(), heroAircraft.getSpeedY()-5);
            assertEquals(bullet.getPower(), 30);
        }
    }
}
