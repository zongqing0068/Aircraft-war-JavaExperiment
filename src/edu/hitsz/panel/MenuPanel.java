package edu.hitsz.panel;

import edu.hitsz.application.AbstractGame;
import edu.hitsz.application.Main;
import edu.hitsz.application.MusicThread;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MenuPanel {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel buttomPanel;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JComboBox musicBox;
    private JLabel musicLabel;

    public MenuPanel() {
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(false);
                synchronized (Main.MAIN_LOCK){
                    // 选定难度，通知主线程结束等待
                    AbstractGame.difficulty = "Easy";
                    Main.MAIN_LOCK.notify();
                }
            }
        });
        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(false);
                synchronized (Main.MAIN_LOCK){
                    // 选定难度，通知主线程结束等待
                    AbstractGame.difficulty = "Medium";
                    Main.MAIN_LOCK.notify();
                }
            }
        });
        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.setVisible(false);
                synchronized (Main.MAIN_LOCK){
                    // 选定难度，通知主线程结束等待
                    AbstractGame.difficulty = "Hard";
                    Main.MAIN_LOCK.notify();
                }
            }
        });

        musicBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if(musicBox.getSelectedItem() == "开"){
                        MusicThread.musicSwitch = true;
                    }
                    else if(musicBox.getSelectedItem() == "关"){
                        MusicThread.musicSwitch = false;
                    }
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("menuPanel");
        frame.setContentPane(new MenuPanel().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
