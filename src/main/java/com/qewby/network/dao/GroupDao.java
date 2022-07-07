package com.qewby.network.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.qewby.network.dto.GroupDto;

public interface GroupDao {
    List<GroupDto> getAllGroups() throws SQLException;

    Optional<GroupDto> getGroupById(final int id) throws SQLException;

    int insertNewGroup(GroupDto groupDto) throws SQLException;

    int updateGroupInfo(GroupDto groupDto) throws SQLException;

    int deleteGroupById(final int id) throws SQLException;
}
