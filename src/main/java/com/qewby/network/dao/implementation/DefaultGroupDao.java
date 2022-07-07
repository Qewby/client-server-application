package com.qewby.network.dao.implementation;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.qewby.network.dao.GroupDao;
import com.qewby.network.dao.mapper.GroupDtoMapper;
import com.qewby.network.dao.mapper.RowMapper;
import com.qewby.network.dto.GroupDto;
import com.qewby.network.executor.SQLExecutor;
import com.qewby.network.executor.SQLiteExecutor;

public class DefaultGroupDao implements GroupDao {

    private SQLExecutor executor = new SQLiteExecutor();

    private static final String GET_ALL_GROUPS = "SELECT * FROM `group`";
    private static final String GET_GROUP_BY_ID = "SELECT * FROM `group` WHERE `group_id` = ?";
    private static final String INSERT_NEW_GROUP = "INSERT INTO `group`\n" +
            "(`group_id`,`group_name`,`group_description`)\n" +
            "VALUES (NULL, ?, ?)";
    private static final String UPDATE_GROUP_INFO = "UPDATE `group`\n" +
            "SET `group_name` = ?," +
            "`group_description` = ?\n" +
            "WHERE `group_id` = ?";
    private static final String DELETE_GROUP_BY_ID = "DELETE FROM `group` WHERE `group_id` = ?";

    @Override
    public List<GroupDto> getAllGroups() throws SQLException {
        RowMapper<GroupDto> mapper = new GroupDtoMapper();
        return executor.executeQuery(GET_ALL_GROUPS, mapper);
    }

    @Override
    public Optional<GroupDto> getGroupById(int id) throws SQLException {
        RowMapper<GroupDto> mapper = new GroupDtoMapper();
        List<Object> parameterList = new LinkedList<>();
        parameterList.add(id);
        List<GroupDto> list = executor.executeQuery(GET_GROUP_BY_ID, parameterList, mapper);
        return list.stream().findFirst();
    }

    @Override
    public int insertNewGroup(GroupDto groupDto) throws SQLException {
        List<Object> parameterList = new LinkedList<>();
        parameterList.add(groupDto.getName());
        parameterList.add(groupDto.getDescription());
        return executor.update(INSERT_NEW_GROUP, parameterList);
    }

    @Override
    public int updateGroupInfo(GroupDto groupDto) throws SQLException {
        List<Object> parameterList = new LinkedList<>();
        parameterList.add(groupDto.getName());
        parameterList.add(groupDto.getDescription());
        parameterList.add(groupDto.getId());
        return executor.update(UPDATE_GROUP_INFO, parameterList);
    }

    @Override
    public int deleteGroupById(int id) throws SQLException {
        List<Object> parameterList = new LinkedList<>();
        parameterList.add(id);
        return executor.update(DELETE_GROUP_BY_ID, parameterList);
    }

}
