package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BloodSupplyProp;
import edu.hitsz.prop.BombSupplyProp;
import edu.hitsz.prop.FireSupplyProp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import edu.hitsz.factory.EliteEnemyFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EliteEnemyTest {

    private EliteEnemyFactory eliteEnemyFactory = new EliteEnemyFactory();
    private AbstractEnemyAircraft eliteEnemy;

    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        eliteEnemy = eliteEnemyFactory.createEnemy();
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
        eliteEnemy = null;
    }

    @Test
    @DisplayName("Test forward method")
    //测试精英敌机类（抽象敌机类）的飞行功能是否正常
    void testForward() {
        System.out.println("**--- This is testForward ---**");
        //起初精英敌机在屏幕内，处于有效状态
        eliteEnemy.forward();
        assertFalse(eliteEnemy.notValid());
        eliteEnemy.setLocation(Main.WINDOW_WIDTH/2, Main.WINDOW_HEIGHT);//抽象敌机类中增加了setLocation方法
        //此时精英敌机飞行到屏幕外，处于失效状态
        eliteEnemy.forward();
        assertTrue(eliteEnemy.notValid());
    }

    @Test
    @DisplayName("Test shoot method")
    //测试精英敌机类的射击功能是否正常
    void testShoot() {
        System.out.println("**--- This is testShoot ---**");
        List<BaseBullet> res = eliteEnemy.executeShoot();
        assertNotNull(res);//成功射出子弹
        for(BaseBullet bullet: res){
            assertEquals(bullet.getLocationX(), eliteEnemy.getLocationX());
            assertEquals(bullet.getLocationY(), eliteEnemy.getLocationY()+2);
            assertEquals(bullet.getSpeedX(), 0);
            assertEquals(bullet.getSpeedY(), eliteEnemy.getSpeedY()+5);
            assertEquals(bullet.getPower(), 30);
        }
    }

    @Test
    @DisplayName("Test produceProp method")
    //测试精英敌机类的产生道具功能是否正常
    void testProduceProp() {
        System.out.println("**--- This is testProduceProp ---**");
        AbstractProp prop;
        boolean haveNull = false, haveBlood = false, haveBomb = false, haveFire = false;
        //当循环次数较大时，可以保证三种道具以及不产生道具的情况均至少出现一次
        for(int i=0; i<50; i++){
            prop = eliteEnemy.produceProp();
            if(prop == null){
                haveNull = true;
                continue;
            }
            //测试道具产生位置是否是当前敌机坠毁位置
            assertEquals(prop.getLocationX(), eliteEnemy.getLocationX());
            assertEquals(prop.getLocationY(), eliteEnemy.getLocationY());
            //测试道具的横纵速度是否满足要求
            assertEquals(prop.getSpeedX(), 0);
            assertEquals(prop.getSpeedY(), 5);
            if(prop instanceof BloodSupplyProp) haveBlood = true;
            else if(prop instanceof BombSupplyProp) haveBomb = true;
            else if(prop instanceof FireSupplyProp) haveFire = true;
        }
        //测试四种情况是否均可能发生
        assertTrue(haveNull && haveBlood && haveBomb && haveFire);
    }
}
