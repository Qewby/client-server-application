package com.qewby.network.service;

import com.qewby.network.dto.GoodDto;
import com.qewby.network.dto.GoodGroupDto;

import java.sql.SQLException;

public interface GoodService {
    GoodDto getGoodById(final String id);

    void createNewGood(final GoodGroupDto goodDto) throws SQLException;

    void updateGoodById(final String id, final GoodGroupDto goodDto);

    void deleteGoodById(String id);
}
