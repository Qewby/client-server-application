package com.qewby.network.converters;

import com.qewby.network.dao.GoodDao;
import com.qewby.network.dao.GroupDao;
import com.qewby.network.dao.implementation.DefaultGoodDao;
import com.qewby.network.dao.implementation.DefaultGroupDao;
import com.qewby.network.dto.GoodDto;
import com.qewby.network.dto.GoodGroupDto;
import com.qewby.network.dto.GroupDto;

import java.sql.SQLException;
import java.util.Optional;

public class GoodConverter {
    private GroupDao groupDao = new DefaultGroupDao();

    public GoodDto convert(final GoodGroupDto goodDto) throws SQLException {
        GoodDto good = new GoodDto();
        good.setId(goodDto.getId());
        good.setPrice(goodDto.getPrice());
        good.setDescription(goodDto.getDescription());
        good.setManufacturer(goodDto.getManufacturer());
        good.setName(goodDto.getName());
        good.setNumber(goodDto.getNumber());
        Optional<GroupDto> groupDto = groupDao.getGroupById(goodDto.getGroup_id());
        good.setGroup(groupDto.get());
        return good;
    }
}
