package service;

import dao.GroupDao;
import entity.Gro;
import impl.GroupDaoImpl;
import util.DbConnection;

import java.util.List;

/**
 * Created by wzzz on 2019/3/17.
 */
public class GroupService {
    private DbConnection dbConnection;
    private GroupDao groupDao;

    public GroupService() {
        this.dbConnection = new DbConnection();
        this.groupDao = new GroupDaoImpl(dbConnection.getConnection());
    }

    public int createGroup(Gro gro) {
        int flag = -1;
        if (!groupDao.isContainGroupByNameAndCreatorId(gro.getName(), gro.getCreatorId())) {
            if (groupDao.createGroup(gro)) {
                return groupDao.getGroupIdByGroupNameAndCreatorId(gro.getName(), gro.getCreatorId());
            }
            ;
        }
        dbConnection.close();
        return flag;
    }

    public boolean deleteGroup(int groupId) {
        boolean flag = groupDao.deleteGroup(groupId);
        dbConnection.close();
        return flag;
    }

    public List<Gro> getAllGroupsByUserId(int userId) {
        List<Gro> list = groupDao.getAllGroupsByUserId(userId);
        dbConnection.close();
        return list;
    }

    public List<Gro> getAllGroupsByCreatorId(int creatorId) {
        List<Gro> list = groupDao.getAllGroupsByCreatorId(creatorId);
        dbConnection.close();
        return list;
    }

    public int joinGroup(int userId, String groupName, String creatorName) {
        int groupId = groupDao.getGroupIdByGroupNameAndCreatorName(groupName.replaceAll("\"", ""), creatorName);
        if (groupId > -1) {
            if (!groupDao.isMemberOfGroup(userId, groupId))
                if (groupDao.joinGroup(userId, groupId))
                    return groupId;
        }
        return -1;
    }
    public boolean joinGroupByAdmin(int userId,int groupId){
        boolean flag=false;
        if(!groupDao.isMemberOfGroup(userId, groupId))
            if(groupDao.joinGroup(userId, groupId))
               flag=true;
        return flag;
    }
}
