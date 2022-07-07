package com.qewby.network.dao;

import java.util.List;
import java.util.Optional;

import com.qewby.network.dto.ProductDto;

public interface ProductDao {
    List<ProductDto> getAllProducts();
    
    List<ProductDto> getProductsByCategoryId(final int categoryId);

    Optional<ProductDto> getProductById(final int id);

    Optional<ProductDto> getProductByName(final String name);

    int insertNewProduct(final ProductDto productDto);

    int updateProductInfo(final ProductDto productDto);

    int deleteProductById(final int id);
}
