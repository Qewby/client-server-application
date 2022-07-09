package com.qewby.network.service.implementation;

import java.sql.SQLException;

import com.qewby.network.dao.GroupDao;
import com.qewby.network.dao.implementation.DefaultGroupDao;
import com.qewby.network.dto.ResponseDto;
import com.qewby.network.service.GroupService;

public class DefaultGroupService implements GroupService {
    private GroupDao groupDao = new DefaultGroupDao();

    @Override
    public ResponseDto getAllGroups() {
        ResponseDto responseDto = new ResponseDto();
        try {
            responseDto.setObject(groupDao.getAllGroups());
            responseDto.setStatus(200);
        } catch (SQLException e) {
            e.printStackTrace();
            responseDto.setObject(null);
            responseDto.setStatus(500);
            responseDto.setErrorMessage("Internal server error");
        }
        return responseDto;
    }
}
