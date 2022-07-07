package com.qewby.network.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.qewby.network.dto.ProductDto;

public interface ProductDao {
    List<ProductDto> getAllProducts() throws SQLException;

    List<ProductDto> getProductsByGroupId(final int groupId) throws SQLException;

    Optional<ProductDto> getProductById(final int id) throws SQLException;

    Optional<ProductDto> getProductByName(final String name) throws SQLException;

    int insertNewProduct(final ProductDto productDto) throws SQLException;

    int updateProductInfoById(final ProductDto productDto) throws SQLException;

    int deleteProductById(final int id) throws SQLException;
}
