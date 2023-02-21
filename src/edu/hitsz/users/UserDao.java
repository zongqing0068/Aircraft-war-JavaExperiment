package edu.hitsz.users;

import java.util.List;

/*
 *用户数据访问对象接口
 */
public interface UserDao {
    void findByName(String userName);

    List<User> getAllUsers();

    void showRankingList();

    void doAdd(User user);

    void doDelet(int id);

    void writeIn();

}
