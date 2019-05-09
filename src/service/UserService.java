package service;

import dao.UserDao;
import entity.User;
import impl.UserDaoImpl;
import util.DbConnection;

import java.sql.Connection;


/**
 * Created by wzzz on 2019/3/17.
 */
public class UserService {
    private DbConnection dbConnection;
    private UserDao userDao;

    public UserService() {
        this.dbConnection = new DbConnection();
        this.userDao = new UserDaoImpl(dbConnection.getConnection());
    }

    public int addUser(User user) {
        int userId=-1;
        User tempUser=userDao.findUserByName(user.getUserName());
        if(tempUser==null){
            userId=userDao.addUser(user);
        }else 
            userId=tempUser.getUserId();
        dbConnection.close();
        return userId;
    }
    public User validateUser(String userName,String userPsd){
        User user=null;
        if(userDao.findUserByName(userName)!=null){
            User temp=userDao.findUserByName(userName);
            if(userPsd.equals(temp.getUserPsd()))
                user=temp;
        }
        dbConnection.close();
        return user;
    }
    public User getUserByName(String userName){
        User user=userDao.findUserByName(userName);
        dbConnection.close();
        return user;
    }
    public boolean isContainUser(String userName){
        return userDao.findUserByName(userName)!=null;
    }
}
