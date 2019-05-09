package impl;

import dao.UserDao;
import entity.User;
import util.DbConnection;

import java.sql.*;

/**
 * Created by wzzz on 2019/3/13.
 */
public class UserDaoImpl implements UserDao {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User findUserByName(String name) {
        User user=null;
        String sql="SELECT * FROM user WHERE user_name =?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()) {
                user=new User();
                int userId = resultSet.getInt(1);
                String userName = resultSet.getString(2);
                int type = resultSet.getInt(3);
                String userPsd = resultSet.getString(4);
                user.setUserId(userId);
                user.setUserName(userName);
                user.setType(type);
                user.setUserPsd(userPsd);
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int addUser(User user) {
        int userId=-1;
        String sql="INSERT INTO user(user_name,type,user_psd) VALUES(?,?,?)";
        try {
            preparedStatement=connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user.getUserName());
            preparedStatement.setInt(2,user.getType());
            preparedStatement.setString(3,user.getUserPsd());
            int result=preparedStatement.executeUpdate();
            if (result >0){
                ResultSet rs = preparedStatement.getGeneratedKeys();
                if (rs.next())
                    userId = rs.getInt(1);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userId;
    }

    @Override
    public boolean deleteUser(int userId) {
        boolean flag=false;
        String sql="DELETE FROM user WHERE user_id=?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            int result=preparedStatement.executeUpdate(sql);
            if(result>0)
                flag=true;
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    //查找所有用户 待添加
}
