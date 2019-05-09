package impl;

import dao.GroupDao;
import entity.Gro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzzz on 2019/3/17.
 */
public class GroupDaoImpl implements GroupDao {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public GroupDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean createGroup(Gro gro) {
        boolean flag=false;
        String sql="INSERT INTO gro(name,creator_id) VALUES(?,?)";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1, gro.getName());
            preparedStatement.setInt(2, gro.getCreatorId());
            int result=preparedStatement.executeUpdate();
            if(result!=-1)
                flag=true;
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }


    @Override
    public boolean deleteGroup(int groupId) {
        boolean flag=false;
        String sql="DELETE FROM gro WHERE group_id=?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setInt(1,groupId);
            int result=preparedStatement.executeUpdate(sql);
            if(result!=-1)
                flag=true;
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<Gro> getAllGroupsByUserId(int userId) {
        List<Gro> list=new ArrayList<>();
        String sql="SELECT g.group_id,g.name FROM gro AS g,user_group AS ug WHERE ug.user_id=? AND g.group_id=ug.group_id";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                Gro gro =new Gro();
                gro.setGroupId(resultSet.getInt(1));
                gro.setName(resultSet.getString(2));
                list.add(gro);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Gro> getAllGroupsByCreatorId(int creatorId) {
        List<Gro> list=new ArrayList<>();
        String sql="SELECT group_id,name FROM gro WHERE creator_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,creatorId);
            ResultSet resultSet=preparedStatement.executeQuery();
            while(resultSet.next()){
                Gro gro =new Gro();
                gro.setGroupId(resultSet.getInt(1));
                gro.setName(resultSet.getString(2));
                list.add(gro);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean isContainGroupByNameAndCreatorId(String name, int creatorId) {
        boolean flag=false;
        String sql="SELECT * FROM gro WHERE name=? AND creator_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,creatorId);
            ResultSet resultSet=preparedStatement.executeQuery();
           if(resultSet.next())
               flag=true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean isMemberOfGroup(int userId,int groupId) {
        boolean flag=false;
        String sql="SELECT * FROM user_group WHERE user_id=? AND group_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,groupId);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
                flag=true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean joinGroup(int userId,int groupId) {
        boolean flag=false;
        String sql="INSERT INTO user_group VALUES(?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userId);
            preparedStatement.setInt(2,groupId);
            int resultSet=preparedStatement.executeUpdate();
            if(resultSet>-1){
                flag=true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public int getGroupIdByGroupNameAndCreatorName(String groupName,String creatorName) {
        String sql="SELECT g.group_id FROM gro AS g,user AS u WHERE g.name=? AND u.user_name=? AND g.creator_id=u.user_id";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,groupName);
            preparedStatement.setString(2,creatorName);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int getGroupIdByGroupNameAndCreatorId(String groupName,int creatorId) {
        String sql="SELECT group_id FROM gro WHERE name=? AND creator_id=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,groupName);
            preparedStatement.setInt(2,creatorId);
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }

}
