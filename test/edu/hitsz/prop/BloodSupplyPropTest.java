package edu.hitsz.prop;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.BloodPropFactory;
import edu.hitsz.factory.EliteEnemyFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BloodSupplyPropTest {

    private BloodPropFactory bloodPropFactory = new BloodPropFactory();
    private AbstractProp bloodSupplyProp;
    int locationX = Main.WINDOW_WIDTH/2;
    int locationY = Main.WINDOW_HEIGHT/2;
    int speedX = 0;
    int speedY = 5;

    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        bloodSupplyProp = bloodPropFactory.createProp(locationX, locationY, speedX, speedY);
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
        bloodSupplyProp = null;
    }

    @Test
    @DisplayName("Test forward method")
    //测试加血道具类（抽象道具类）的移动功能是否正常
    void testForward() {
        System.out.println("**--- This is testForward ---**");
        //初始时道具处于屏幕中央的安全位置，此时道具有效
        bloodSupplyProp.forward();
        assertFalse(bloodSupplyProp.notValid());

        //测试道具在向下掉落出界后是否失效
        bloodSupplyProp = bloodPropFactory.createProp(locationX, Main.WINDOW_HEIGHT, speedX, speedY);
        bloodSupplyProp.forward();
        assertTrue(bloodSupplyProp.notValid());

    }

}