package edu.hitsz.users;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
 *用户对象类
 */
public class User {
    /*用户名*/
    private String userName;
    /*用户的游戏得分*/
    private int score;
    /*用户的记录时间*/
    private String time;

    public User(String userName, int score, String time){
        this.userName = userName;
        this.score = score;
        this.time = time;
    }
    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

}
