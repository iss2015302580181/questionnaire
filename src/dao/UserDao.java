package dao;

import entity.User;

/**
 * Created by wzzz on 2019/3/13.
 */
public interface UserDao {
    User findUserByName(String name);
    int addUser(User user);
    boolean deleteUser(int userId);
}
