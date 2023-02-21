package edu.hitsz.panel;

import edu.hitsz.application.AbstractGame;
import edu.hitsz.users.User;
import edu.hitsz.users.UserDao;
import edu.hitsz.users.UserDaoImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BoardPanel {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel buttomPanel;
    private JLabel headerLabel;
    private JScrollPane tableScrollPanel;
    private JTable scoreTable;
    private JButton deleteButton;
    private JLabel difficultyLabel;
    private List<User> allUsers = new LinkedList<>();
    private final UserDao userDao = new UserDaoImpl();
    private String[][] tableData;
    /**设置表格列名*/
    private final String[] columnName = {"名次","玩家名","得分","记录时间"};
    /*表格模型*/
    DefaultTableModel model;

    public BoardPanel() {
        /*设置难度标签*/
        difficultyLabel.setText("难度：" + AbstractGame.difficulty);
        /*获取排好名的用户信息*/
        tableData = maketableData();
        model = new DefaultTableModel(tableData, columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        /*从表格模型那里获取数据*/
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = scoreTable.getSelectedRow();
                System.out.println(row);
                if(row != -1){
                    if(JOptionPane.showConfirmDialog(null, "是否确定删除选中的玩家？", "选择一个选项", JOptionPane.YES_NO_CANCEL_OPTION)==JOptionPane.YES_OPTION){
                        model.removeRow(row);
                        userDao.doDelet(row);
                        /*刷新排行榜并将信息写入文件*/
                        refresh();
                    }
                }
            }
        });
    }

    /*弹出弹窗并录入当前用户信息*/
    public void addNewUser(){
        /*弹出弹窗*/
        String name = JOptionPane.showInputDialog(mainPanel, "游戏结束，你的得分为"+ AbstractGame.getScore()+
                ",\n请输入名字记录得分：" , "输入", JOptionPane.PLAIN_MESSAGE);
        /*获取当前时间*/
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        /*加入当前用户信息*/
        userDao.doAdd(new User(name, AbstractGame.getScore(), dateFormat.format(date)));
        /*刷新排行榜并将信息写入文件*/
        refresh();
    }

    /*刷新排行榜并将信息写入文件*/
    public void refresh(){
        userDao.writeIn();
        tableData = maketableData();
        model = new DefaultTableModel(tableData, columnName){
            @Override
            public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        /*从表格模型那里获取数据*/
        scoreTable.setModel(model);
        tableScrollPanel.setViewportView(scoreTable);
    }

    /*获取全部历史用户信息并返回数据表*/
    private String[][] maketableData(){
        allUsers = userDao.getAllUsers();
        String[][] tableData = new String[allUsers.size()][4];
        int i = 1;
        for(User user : allUsers){
            tableData[i-1][0] = Integer.toString(i);
            tableData[i-1][1] = user.getUserName();
            tableData[i-1][2] = Integer.toString(user.getScore());
            tableData[i-1][3] = user.getTime();
            i++;
        }
        return tableData;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Aircraft War");
        BoardPanel boardPanel = new BoardPanel();
        JPanel boardMainPanel = boardPanel.getMainPanel();
        frame.setContentPane(boardMainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        boardPanel.addNewUser();

    }
}
