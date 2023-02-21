package edu.hitsz.application;

import edu.hitsz.panel.BoardPanel;
import edu.hitsz.panel.MenuPanel;

import javax.swing.*;
import java.awt.*;

/**
 * 程序入口
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Object MAIN_LOCK = new Object();

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");

        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //menu界面
        MenuPanel menuPanel = new MenuPanel();
        JPanel menuMainPanel = menuPanel.getMainPanel();
        frame.setContentPane(menuMainPanel);
        frame.setVisible(true);

        synchronized (MAIN_LOCK) {
            while (menuMainPanel.isVisible()) {
                // 主线程等待菜单面板关闭
                try {
                    MAIN_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 移除第一个菜单panel，后面加入Game Panel，实现界面切换
        frame.remove(menuMainPanel);

        AbstractGame game;
        //Game界面
        switch (AbstractGame.difficulty){
            case "Easy":
                game = new EasyGame();
                break;
            case "Medium":
                game = new MediumGame();
                break;
            case "Hard":
                game = new HardGame();
                break;
            default:
                game = new EasyGame();
                break;
        }

        frame.setContentPane(game);
        frame.setVisible(true);
        game.action();

        synchronized (MAIN_LOCK) {
            while (game.isVisible()) {
                // 主线程等待Game面板关闭
                try {
                    MAIN_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // 移除第二个Game panel，后面加入排行榜Panel，实现界面切换
        frame.remove(game);

        //排行榜界面
        BoardPanel boardPanel = new BoardPanel();
        JPanel boardMainPanel = boardPanel.getMainPanel();
        frame.setContentPane(boardMainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        boardPanel.addNewUser();


    }
}
