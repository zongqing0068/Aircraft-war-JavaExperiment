package edu.hitsz.application;

import edu.hitsz.aircraft.*;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.AbstractEnemyFactory;
import edu.hitsz.factory.BossEnemyFactory;
import edu.hitsz.factory.EliteEnemyFactory;
import edu.hitsz.factory.MobEnemyFactory;
import edu.hitsz.observer.Subscriber;
import edu.hitsz.prop.AbstractProp;
import edu.hitsz.prop.BombSupplyProp;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class AbstractGame extends JPanel {

    /**游戏难度*/
    public static String difficulty;

    /**生效的火力道具的数量，用于火力道具的时间叠加*/
    public static int firePropNum=0;

    protected int backGroundTop = 0;

    /**Scheduled 线程池，用于任务调度*/
    protected final ScheduledExecutorService executorService;

    /**时间间隔(ms)，控制刷新频率*/
    protected int timeInterval = 40;

    /**时间间隔(ms)，控制难度更新频率*/
    protected int diffChangeDuration = 5000;

    protected final HeroAircraft heroAircraft;
    protected final List<AbstractEnemyAircraft> enemyAircrafts;
    protected final List<BaseBullet> heroBullets;
    protected final List<BaseBullet> enemyBullets;
    protected final List<AbstractProp> props;
    protected AbstractEnemyFactory enemyFactory;
    private static int score = 0;
    private int time = 0;

    /**同时出现的敌机最大数量*/
    protected int enemyMaxNumber;

    /**精英敌机产生的概率*/
    protected double enemyRate;

    /**周期（ms)，指示子弹的发射、敌机的产生频率*/
    protected int cycleDuration;
    protected int cycleTime = 0;
    protected int diffCycleTime = 0;

    protected MusicThread bgmMusic;
    protected MusicThread bgmBossMusic;

    /**boss敌机出现的阈值*/
    protected int bossScoreThreshold;

    /**标志boss敌机是否在屏幕上*/
    protected boolean bossOnScreen = false;

    /**boss敌机产生过的次数，用于逐次提升boss机血量*/
    protected int bossNum = 0;

    public static int getScore(){
        return score;
    }

    public AbstractGame() {
        heroAircraft = HeroAircraft.getHeroAircraft();
        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        /*Scheduled 线程池，用于定时任务调度*/
        ThreadFactory gameThread = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("Game Thread");
                return t;
            }
        };

        executorService = new ScheduledThreadPoolExecutor(1, gameThread);

        /*启动英雄机鼠标监听*/
        new HeroController(this, heroAircraft);

        /*播放背景音乐*/
        bgmMusic = new MusicThread("src/videos/bgm.wav");
        bgmMusic.start();

    }

    /**
     * 游戏启动入口，执行游戏逻辑。模板模式的模板方法
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            // 计时
            timeCountAction();

            // 控制背景音乐和boss敌机音乐等循环播放
            musicControlAction();

            // 改变难度
            changeDiffAction();

            // 产生普通敌机和精英敌机
            createMobAndEliteAction();

            // 产生boss敌机
            createBossAction();

            // 飞机射出子弹
            shootAction();

            // 更新时间
            newTimeAction();

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查
            gameOverCheckAction();

        };

        /*
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    //***********************
    //      Action 各部分
    //***********************

    /** 计时并周期性输出时间*/
    private void timeCountAction(){
        time += timeInterval;
        cycleTime += timeInterval;
        diffCycleTime += timeInterval;
        if (newCycleJudge()){
            System.out.println(time);
        }
    }

    /** 控制背景音乐和boss敌机音乐等循环播放*/
    private void musicControlAction(){
        /*设置背景音乐循环播放*/
        if(!bgmMusic.isAlive()){
            bgmMusic = new MusicThread("src/videos/bgm.wav");
            bgmMusic.start();
        }

        /*设置boss敌机背景音乐循环播放*/
        if(bossOnScreen && !bgmBossMusic.isAlive()){
            bgmBossMusic = new MusicThread("src/videos/bgm_boss.wav");
            bgmBossMusic.start();
        }
    }

    /** 难度周期性提升*/
    protected abstract void changeDiffAction();

    /** 产生普通敌机和精英敌机*/
    private void createMobAndEliteAction(){
        if (newCycleJudge()){
            if (enemyAircrafts.size() < enemyMaxNumber) {
                double r = Math.random();
                if(r>enemyRate){
                    enemyFactory = new MobEnemyFactory();
                    enemyAircrafts.add(enemyFactory.createEnemy());
                }
                else{
                    enemyFactory = new EliteEnemyFactory();
                    enemyAircrafts.add(enemyFactory.createEnemy());
                }
            }
        }
    }

    /** 产生boss敌机*/
    protected abstract void createBossAction();


    /** 周期性执行（控制频率）*/
    protected boolean newCycleJudge() {
        return (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime);
    }

    /** 控制难度改变频率*/
    protected boolean diffChangeJudge() {
        return (diffCycleTime >= diffChangeDuration && diffCycleTime - timeInterval < diffCycleTime);
    }

    /**判断是否更新到新的周期*/
    private void newTimeAction(){
        if(newCycleJudge()){
            // 跨越到新的周期
            cycleTime %= cycleDuration;
        }
        if(diffChangeJudge()){
            // 跨越到新的周期
            diffCycleTime %= diffChangeDuration;
        }
    }

    /** 飞机射出子弹*/
    private void shootAction() {
        if (newCycleJudge()){
            // TODO 敌机射击
            for(AbstractEnemyAircraft enemyAircraft :enemyAircrafts) {
                if(enemyAircraft instanceof EliteEnemy){
                    enemyBullets.addAll(enemyAircraft.executeShoot());
                }
                if(enemyAircraft instanceof BossEnemy){
                    enemyBullets.addAll(enemyAircraft.executeShoot());
                }
            }

            // 英雄射击
            heroBullets.addAll(heroAircraft.executeShoot());
        }
    }

    /** 子弹移动*/
    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    /** 飞机移动*/
    private void aircraftsMoveAction() {
        for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    /** 道具移动*/
    private void propsMoveAction() {
        for (AbstractProp prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet enemybullet : enemyBullets) {
            if (enemybullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(enemybullet)) {
                // 英雄机撞击到敌机子弹
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(enemybullet.getPower());
                enemybullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet herobullet : heroBullets) {
            if (herobullet.notValid()) {
                continue;
            }
            for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(herobullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(herobullet.getPower());
                    herobullet.vanish();
                    MusicThread hitMusic= new MusicThread("src/videos/bullet_hit.wav");
                    hitMusic.start();
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        AbstractProp prop = enemyAircraft.produceProp();
                        if(prop != null){
                            props.add(prop);
                        }

                        if(enemyAircraft instanceof MobEnemy){
                            score += 5;
                        }
                        else if(enemyAircraft instanceof EliteEnemy){
                            score += 10;
                        }
                        else if(enemyAircraft instanceof BossEnemy){
                            bgmBossMusic.setMusicInterrupt(true);
                            bossOnScreen = false;
                            score += 20;
                        }
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProp prop : props){
            if(heroAircraft.crash(prop)) {
                /*获得道具的音效*/
                MusicThread propMusic = new MusicThread("src/videos/get_supply.wav");
                propMusic.start();
                /*道具生效*/
                if(prop instanceof BombSupplyProp){
                    for (AbstractEnemyAircraft enemyAircraft : enemyAircrafts){
                        if(enemyAircraft instanceof MobEnemy){
                            score += 5;
                            ((BombSupplyProp) prop).addSubscriber((Subscriber) enemyAircraft);
                        }
                        else if(enemyAircraft instanceof EliteEnemy){
                            score += 10;
                            ((BombSupplyProp) prop).addSubscriber((Subscriber) enemyAircraft);
                        }
                    }
                    for (BaseBullet baseBullet : enemyBullets){
                        ((BombSupplyProp) prop).addSubscriber((Subscriber) baseBullet);
                    }
                }
                prop.function(heroAircraft);
            }
        }
    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * 3. 检查英雄机生存
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }

    /** 游戏结束检查*/
    private void gameOverCheckAction(){
        if (heroAircraft.getHp() <= 0) {
            // 游戏结束
            executorService.shutdown();
            //关闭背景音乐
            bgmMusic.setMusicInterrupt(true);
            //若boss敌机还在屏幕上，则关闭boss敌机背景音乐
            if(bossOnScreen){
                bgmBossMusic.setMusicInterrupt(true);
            }
            MusicThread gameOverMusic = new MusicThread("src/videos/game_over.wav");
            gameOverMusic.start();

            System.out.println("Game Over!");

            this.setVisible(false);
            synchronized (Main.MAIN_LOCK){
                //通知主线程结束等待
                Main.MAIN_LOCK.notify();
            }
        }
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //绘制背景图
        paintBackground(g);
        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);
        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    /**绘制背景，图片滚动*/
    protected abstract void paintBackground(Graphics g);

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
