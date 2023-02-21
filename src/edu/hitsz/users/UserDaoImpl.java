package edu.hitsz.users;

import edu.hitsz.application.AbstractGame;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/*
 *用户数据访问对象实体类
 */
public class UserDaoImpl implements UserDao{
    private List<User> allUsers = new LinkedList<>();

    public UserDaoImpl(){
        /*读取历史用户信息的文件，若文件不存在，则allUsers列表仍为空*/
        try {
            BufferedReader bufferedReader = null;
            FileInputStream fileInputStream = new FileInputStream("src/edu/hitsz/users/allUsers"+ AbstractGame.difficulty+".txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] info = line.split(";");
                User player = new User(info[0], Integer.parseInt(info[1]), info[2]);
                allUsers.add(player);
            }
            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            System.out.println("读入历史用户信息");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*返回所有排好序的用户信息列表*/
    @Override
    public List<User> getAllUsers() {
        /*将所有用户按照分数降序排列*/
        allUsers.sort((o1, o2) -> o2.getScore() - o1.getScore());
        return allUsers;
    }

    /*查找某用户的记录*/
    @Override
    public void findByName(String userName) {
        for(User user : allUsers){
            if(Objects.equals(user.getUserName(), userName)){
                System.out.println("Find userName["+userName+"],score["+user.getScore()+"],time["+user.getTime()+"]");
                return;
            }
        }
        System.out.println("Can not find this user");
    }

    /*输出所有用户的得分排行榜，按照分数降序排列*/
    @Override
    public void showRankingList() {
        System.out.println("********************************");
        System.out.println("            得分排行榜            ");
        System.out.println("********************************");
        /*将所有用户按照分数降序排列*/
        allUsers.sort((o1, o2) -> o2.getScore() - o1.getScore());
        int i = 1;
        for(User user : allUsers){
            System.out.println("第"+i+"名,name:"+user.getUserName()+",score:"+user.getScore()+",time:"+user.getTime());
            i++;
        }
        /*将所有用户信息写入文件*/
        writeIn();
    }

    /*加入当前用户的信息*/
    @Override
    public void doAdd(User user) {
        allUsers.add(user);
        System.out.println("Add new userName["+user.getUserName()+"],score["+user.getScore()+"],time["+user.getTime()+"]");

    }

    /*删除某用户的记录*/
    @Override
    public void doDelet(int id) {
        allUsers.remove(id);
    }

    /*写入所有用户信息*/
    @Override
    public void writeIn(){
        /*将所有用户信息写入文件*/
        try {
            BufferedWriter bufferedWriter = null;
            FileOutputStream fileOutputStream = new FileOutputStream("src/edu/hitsz/users/allUsers"+ AbstractGame.difficulty+".txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            String line;
            for(User userNew : allUsers){
                line = userNew.getUserName()+";"+userNew.getScore()+";"+userNew.getTime()+"\n";
                bufferedWriter.write(line);
            }
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
            System.out.println("写入所有用户信息");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
