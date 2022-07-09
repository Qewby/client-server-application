package com.qewby.network.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.qewby.network.dao.RowMapper;
import com.qewby.network.dto.GoodDto;

public class GoodDtoMapper implements RowMapper<GoodDto> {

    private static final String GOOD_ID = "good_id";
    private static final String GOOD_NAME = "good_name";
    private static final String GOOD_DESCRIPTION = "good_description";
    private static final String MANUFACTURER = "manufacturer";
    private static final String NUMBER = "number";
    private static final String PRICE = "price";

    private static final GroupDtoMapper groupMapper = new GroupDtoMapper();

    @Override
    public GoodDto map(ResultSet rs) throws SQLException {
        GoodDto goodDto = new GoodDto();
        goodDto.setId(rs.getInt(GOOD_ID));
        goodDto.setName(rs.getString(GOOD_NAME));
        goodDto.setGroup(groupMapper.map(rs)); 
        goodDto.setDescription(rs.getString(GOOD_DESCRIPTION));
        goodDto.setManufacturer(rs.getString(MANUFACTURER));
        goodDto.setNumber(rs.getInt(NUMBER));
        goodDto.setPrice(rs.getBigDecimal(PRICE));
        return goodDto;
    }
    
}
