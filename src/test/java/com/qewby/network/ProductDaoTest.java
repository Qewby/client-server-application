package com.qewby.network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qewby.network.dao.GroupDao;
import com.qewby.network.dao.ProductDao;
import com.qewby.network.dao.implementation.DefaultGroupDao;
import com.qewby.network.dao.implementation.DefaultProductDao;
import com.qewby.network.dto.GroupDto;
import com.qewby.network.dto.ProductDto;

public class ProductDaoTest {
    private static final GroupDao groupDao = new DefaultGroupDao();
    private static final ProductDao productDao = new DefaultProductDao();

    private static final String testDatabaseName = "test.db";

    @Before
    public void createTestDatabase() throws SQLException {
        Application application = new Application();
        application.initializeDatabase(testDatabaseName);

        GroupDto groupDto = new GroupDto();
        String name = "Овочі";
        groupDto.setName(name);
        groupDao.insertNewGroup(groupDto);
        name = "Крупи";
        groupDto.setName(name);
        groupDto.setDescription("Гречка, вівсянка та ін.");
        groupDao.insertNewGroup(groupDto);

        ProductDto productDto = new ProductDto();
        productDto.setName("Огірок");
        productDto.setGroup(new GroupDto(1, null, null));
        productDto.setNumber(10);
        productDto.setPrice(new BigDecimal("2.63"));
        productDao.insertNewProduct(productDto);

        productDto.setName("Томат");
        productDao.insertNewProduct(productDto);

        productDto.setName("Гречка");
        productDto.setGroup(new GroupDto(2, null, null));
        productDao.insertNewProduct(productDto);
    }

    @Test
    public void mustBeThreeRecordsInProduct() throws SQLException {
        List<ProductDto> list = productDao.getAllProducts();
        for (ProductDto i : list) {
            System.out.println(i.toString());
        }
        int actual = list.size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    public void getByGroupShouldReturnOnlyProductsWithSameGroup() throws SQLException {
        Integer groupId = 1;
        List<ProductDto> list = productDao.getProductsByGroupId(groupId);
        assertEquals(2, list.size());
        for (ProductDto prod : list) {
            assertEquals(prod.getGroup().getId(), groupId);
        }
    }

    @Test
    public void testGetProductById() throws SQLException {
        List<ProductDto> list = productDao.getAllProducts();
        ProductDto expected = list.get(1);
        ProductDto actual = productDao.getProductById(expected.getId()).orElseThrow();
        assertEquals(expected, actual);
    }

    @Test
    public void testGetProductByName() throws SQLException {
        List<ProductDto> list = productDao.getAllProducts();
        ProductDto expected = list.get(1);
        ProductDto actual = productDao.getProductByName(expected.getName()).orElseThrow();
        assertEquals(expected, actual);
    }

    @Test
    public void testUpdateProduct() throws SQLException {
        List<ProductDto> list = productDao.getAllProducts();
        ProductDto beforeUpdate = list.get(1);
        ProductDto expected = beforeUpdate.withName("Перець");
        productDao.updateProductInfoById(expected);
        ProductDto actual = productDao.getProductById(beforeUpdate.getId()).orElseThrow();
        assertEquals(expected, actual);
    }

    @Test
    public void testDeleteProduct() throws SQLException {
        List<ProductDto> beforeDelete = productDao.getAllProducts();
        assertEquals(3, beforeDelete.size());
        productDao.deleteProductById(beforeDelete.get(1).getId());
        List<ProductDto> afterDelete = productDao.getAllProducts();
        assertEquals(2, afterDelete.size());
    }

    @Test(expected = SQLException.class)
    public void throwIfInsertNameAlreadyExists() throws SQLException {
        List<ProductDto> all = productDao.getAllProducts();
        productDao.insertNewProduct(all.get(0));
    }
    
    @Test
    public void noValuesIfGroupIdNotExists() throws SQLException {
        List<ProductDto> list = productDao.getProductsByGroupId(1000);
        assertEquals(0, list.size());
    }

    @Test
    public void noValueIfIdNotExists() throws SQLException {
        boolean condition = productDao.getProductById(1000).isPresent();
        assertFalse(condition);
    }

    @Test
    public void noValueIfNameNotExists() throws SQLException {
        boolean condition = productDao.getProductByName("HLSDFJLS").isPresent();
        assertFalse(condition);
    }

    @Test
    public void productsDeleteIfGroupDelete() throws SQLException {
        int groupId = 1;
        List<ProductDto> beforeList = productDao.getProductsByGroupId(groupId);
        assertEquals(2, beforeList.size());
        groupDao.deleteGroupById(groupId); 
        List<ProductDto> afterList = productDao.getProductsByGroupId(groupId);
        assertEquals(0, afterList.size());
    }

    @After
    public void deleteTestDatabase() {
        File file = new File(testDatabaseName);
        file.delete();
    }
}
