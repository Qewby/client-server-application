package com.qewby.network.service.implementation;

import java.sql.SQLException;
import java.sql.Timestamp;

import com.qewby.network.dao.GroupDao;
import com.qewby.network.dao.implementation.DefaultGroupDao;
import com.qewby.network.dto.ErrorMessageDto;
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
            responseDto.setStatus(500);
            responseDto.setError(
                    new ErrorMessageDto(500, "Internal server error", new Timestamp(System.currentTimeMillis())));
        }
        return responseDto;
    }
}
