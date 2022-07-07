package com.qewby.network.dao.implementation;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.qewby.network.dao.ProductDao;
import com.qewby.network.dao.RowMapper;
import com.qewby.network.dao.mapper.ProductDtoMapper;
import com.qewby.network.dto.ProductDto;
import com.qewby.network.executor.SQLExecutor;
import com.qewby.network.executor.implementation.SQLiteExecutor;

public class DefaultProductDao implements ProductDao {

    private SQLExecutor executor = new SQLiteExecutor();

    private static final String GET_ALL_PRODUCTS = "SELECT * FROM `product`\n" +
            "INNER JOIN `group` ON `group`.`group_id` = `product`.`group_id`";
    private static final String GET_PRODUCTS_BY_GROUP_ID = "SELECT * FROM `product`\n" +
            "INNER JOIN `group` ON `group`.`group_id` = `product`.`group_id`\n" +
            "WHERE `product`.`group_id` = ?";
    private static final String GET_PRODUCT_BY_ID = "SELECT * FROM `product`\n" +
            "INNER JOIN `group` ON `group`.`group_id` = `product`.`group_id`\n" +
            "WHERE `product`.`product_id` = ?";
    private static final String GET_PRODUCT_BY_NAME = "SELECT * FROM `product`\n" +
            "INNER JOIN `group` ON `group`.`group_id` = `product`.`group_id`\n" +
            "WHERE `product`.`product_name` = ?";
    private static final String INSERT_NEW_PRODUCT = "INSERT INTO `product`\n" +
            "(`product_id`," +
            "`product_name`," +
            "`group_id`," +
            "`product_description`," +
            "`manufacturer`," +
            "`number`," +
            "`price`)\n" +
            "VALUES (NULL, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT_INFO_BY_ID = "UPDATE `product`\n" +
            "SET `product_name` = ?," +
            "`group_id` = ?," +
            "`product_description` = ?," +
            "`manufacturer` = ?," +
            "`number` = ?," +
            "`price` = ?\n" +
            "WHERE `product`.`product_id` = ?";
    private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM `product` WHERE `product`.`product_id` = ?";

    @Override
    public List<ProductDto> getAllProducts() throws SQLException {
        RowMapper<ProductDto> mapper = new ProductDtoMapper();
        return executor.executeQuery(GET_ALL_PRODUCTS, mapper);
    }

    @Override
    public List<ProductDto> getProductsByGroupId(final int groupId) throws SQLException {
        RowMapper<ProductDto> mapper = new ProductDtoMapper();
        List<Object> parameterList = new LinkedList<>();
        parameterList.add(groupId);
        return executor.executeQuery(GET_PRODUCTS_BY_GROUP_ID, parameterList, mapper);
    }

    @Override
    public Optional<ProductDto> getProductById(final int id) throws SQLException {
        RowMapper<ProductDto> mapper = new ProductDtoMapper();
        List<Object> parameterList = new LinkedList<>();
        parameterList.add(id);
        List<ProductDto> list = executor.executeQuery(GET_PRODUCT_BY_ID, parameterList, mapper);
        return list.stream().findFirst();
    }

    @Override
    public Optional<ProductDto> getProductByName(final String name) throws SQLException {
        RowMapper<ProductDto> mapper = new ProductDtoMapper();
        List<Object> parameterList = new LinkedList<>();
        parameterList.add(name);
        List<ProductDto> list = executor.executeQuery(GET_PRODUCT_BY_NAME, parameterList, mapper);
        return list.stream().findFirst();
    }

    @Override
    public int insertNewProduct(final ProductDto productDto) throws SQLException {
        List<Object> parameterList = new LinkedList<>();
        parameterList.add(productDto.getName());
        parameterList.add(productDto.getGroup().getId());
        parameterList.add(productDto.getDescription());
        parameterList.add(productDto.getManufacturer());
        parameterList.add(productDto.getNumber());
        parameterList.add(productDto.getPrice());
        return executor.update(INSERT_NEW_PRODUCT, parameterList);
    }

    @Override
    public int updateProductInfoById(final ProductDto productDto) throws SQLException {
        List<Object> parameterList = new LinkedList<>();
        parameterList.add(productDto.getName());
        parameterList.add(productDto.getGroup().getId());
        parameterList.add(productDto.getDescription());
        parameterList.add(productDto.getManufacturer());
        parameterList.add(productDto.getNumber());
        parameterList.add(productDto.getPrice());
        parameterList.add(productDto.getId());
        return executor.update(UPDATE_PRODUCT_INFO_BY_ID, parameterList);
    }

    @Override
    public int deleteProductById(final int id) throws SQLException {
        List<Object> parameterList = new LinkedList<>();
        parameterList.add(id);
        return executor.update(DELETE_PRODUCT_BY_ID, parameterList);
    }
}
