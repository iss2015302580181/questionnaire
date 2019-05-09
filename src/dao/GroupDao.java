package dao;

import entity.Gro;

import java.util.List;

/**
 * Created by wzzz on 2019/3/17.
 */
public interface GroupDao {
    boolean createGroup(Gro gro);

    boolean deleteGroup(int groupId);

    List<Gro> getAllGroupsByUserId(int userId);

    List<Gro> getAllGroupsByCreatorId(int creatorId);

    boolean isContainGroupByNameAndCreatorId(String name, int creatorId);

    boolean isMemberOfGroup(int userId, int groupId);

    boolean joinGroup(int userId, int groupId);

    int getGroupIdByGroupNameAndCreatorName(String groupName, String creatorName);

    int getGroupIdByGroupNameAndCreatorId(String groupName, int creatorId);
}
