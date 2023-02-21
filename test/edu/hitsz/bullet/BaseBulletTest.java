package edu.hitsz.bullet;

import edu.hitsz.aircraft.AbstractEnemyAircraft;
import edu.hitsz.application.Main;
import edu.hitsz.factory.EliteEnemyFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseBulletTest {

    private BaseBullet baseBullet;
    int locationX = Main.WINDOW_WIDTH/2;
    int locationY = Main.WINDOW_HEIGHT/2;
    int speedX = 0;
    int speedY = 15;
    int power = 10;

    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        baseBullet = new BaseBullet(locationX, locationY, speedX, speedY, power);
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
        baseBullet = null;
    }

    @Test
    @DisplayName("Test forward method")
    //测试子弹类的飞行功能是否正常
    void testForward() {
        System.out.println("**--- This is testForward ---**");
        //初始时子弹处于屏幕中央的安全位置，此时子弹有效
        baseBullet.forward();
        assertFalse(baseBullet.notValid());

        //测试子弹在x轴左侧出界后是否失效
        baseBullet = new BaseBullet(0, locationY, speedX, speedY, power);
        baseBullet.forward();
        assertTrue(baseBullet.notValid());

        //测试子弹在x轴右侧出界后是否失效
        baseBullet = new BaseBullet(Main.WINDOW_WIDTH, locationY, speedX, speedY, power);
        baseBullet.forward();
        assertTrue(baseBullet.notValid());

        //测试子弹在向下飞行出界后是否失效
        baseBullet = new BaseBullet(locationX, Main.WINDOW_HEIGHT, speedX, speedY, power);
        baseBullet.forward();
        assertTrue(baseBullet.notValid());

        //若子弹处于下边界，但纵向速度小于等于0，说明子弹在向上飞行，则仍有效
        baseBullet = new BaseBullet(locationX, Main.WINDOW_HEIGHT, speedX, -speedY, power);
        baseBullet.forward();
        assertFalse(baseBullet.notValid());

        //测试子弹在向上飞行出界后是否失效
        baseBullet = new BaseBullet(locationX, 0, speedX, -speedY, power);
        baseBullet.forward();
        assertTrue(baseBullet.notValid());

    }

    @Test
    @DisplayName("Test getPower method")
    //测试子弹类的获取子弹伤害功能是否正常
    void testGetPower() {
        System.out.println("**--- This is testGetPower ---**");
        assertEquals(power, baseBullet.getPower());
    }
}