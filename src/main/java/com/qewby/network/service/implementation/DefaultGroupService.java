package com.qewby.network.service.implementation;

import java.sql.SQLException;
import java.util.List;

import com.qewby.network.dao.GroupDao;
import com.qewby.network.dao.implementation.DefaultGroupDao;
import com.qewby.network.dto.GroupDto;
import com.qewby.network.exception.ResponseErrorException;
import com.qewby.network.service.GroupService;

public class DefaultGroupService implements GroupService {
    private GroupDao groupDao = new DefaultGroupDao();

    @Override
    public List<GroupDto> getAllGroups() {
        try {
            List<GroupDto> result = groupDao.getAllGroups();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseErrorException(500);
        }
    }
}
