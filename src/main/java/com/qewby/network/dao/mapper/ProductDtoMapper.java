package com.qewby.network.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.qewby.network.dto.ProductDto;

public class ProductDtoMapper implements RowMapper<ProductDto> {

    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_DESCRIPTION = "product_description";
    private static final String MANUFACTURER = "manufacturer";
    private static final String NUMBER = "number";
    private static final String PRICE = "price";

    private static final GroupDtoMapper groupMapper = new GroupDtoMapper();

    @Override
    public ProductDto map(ResultSet rs) throws SQLException {
        ProductDto productDto = new ProductDto();
        productDto.setId(rs.getInt(PRODUCT_ID));
        productDto.setName(rs.getString(PRODUCT_NAME));
        productDto.setGroup(groupMapper.map(rs)); 
        productDto.setDescription(rs.getString(PRODUCT_DESCRIPTION));
        productDto.setManufacturer(rs.getString(MANUFACTURER));
        productDto.setNumber(rs.getInt(NUMBER));
        productDto.setPrice(rs.getBigDecimal(PRICE));
        return productDto;
    }
    
}
