package com.qewby.network.dao.implementation;

import java.util.List;
import java.util.Optional;

import com.qewby.network.dao.ProductDao;
import com.qewby.network.dto.ProductDto;

public class DefaultProductDao implements ProductDao {

    @Override
    public List<ProductDto> getAllProducts() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProductDto> getProductsByCategoryId(int categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<ProductDto> getProductById(int id) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public Optional<ProductDto> getProductByName(String name) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public int insertNewProduct(ProductDto productDto) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int updateProductInfo(ProductDto productDto) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int deleteProductById(int id) {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
