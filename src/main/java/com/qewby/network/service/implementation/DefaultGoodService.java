package com.qewby.network.service.implementation;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

import com.qewby.network.converters.GoodConverter;
import com.qewby.network.dao.GoodDao;
import com.qewby.network.dao.implementation.DefaultGoodDao;
import com.qewby.network.dto.GoodDto;
import com.qewby.network.dto.GoodGroupDto;
import com.qewby.network.exception.ResponseErrorException;
import com.qewby.network.service.GoodService;

public class DefaultGoodService implements GoodService {

    private GoodDao goodDao = new DefaultGoodDao();
    private GoodConverter goodConverter = new GoodConverter();
    @Override
    public GoodDto getGoodById(final String stringId) {
        try {
            final int id = Integer.valueOf(stringId);
            Optional<GoodDto> goodDto = goodDao.getGoodById(id);
            if (goodDto.isPresent()) {
                return goodDto.get();
            } else {
                throw new ResponseErrorException(404, "No good with such id");
            }
        } catch (NumberFormatException e) {
            throw new ResponseErrorException(400, "ID is not an integer number");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseErrorException(500);
        }
    }

    @Override
    public void createNewGood(GoodGroupDto goodDto) throws SQLException {
        GoodDto created = goodConverter.convert(goodDto);
        goodDao.insertNewGood(created);
    }

    @Override
    public void updateGoodById(String stringId, GoodGroupDto goodDto) {
        try {
            GoodDto updatedGood = goodConverter.convert(goodDto);
            GoodDto originalGood = goodDao.getGoodById(Integer.parseInt(stringId)).get();
            for (Field field : updatedGood.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(updatedGood);
                String fieldName = field.getName();
                if (value == null) {
                    Field field1 = originalGood.getClass().getDeclaredField(fieldName);
                    field1.setAccessible(true);
                    Object value1 = field1.get(originalGood);
                    field.set(updatedGood, value1);
                }
            }

            int rowCount = goodDao.updateGoodInfoById(updatedGood);
            if (rowCount != 1) {
                throw new ResponseErrorException(404, "No good with such id");
            }
        } catch (NumberFormatException e) {
            throw new ResponseErrorException(400, "ID is not an integer number");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseErrorException(500);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGoodById(final String stringId) {
        try {
            final int id = Integer.valueOf(stringId);
            int rowCount = goodDao.deleteGoodById(id);
            if (rowCount != 1) {
                throw new ResponseErrorException(404, "No good with such id");
            }
        } catch (NumberFormatException e) {
            throw new ResponseErrorException(400, "ID is not an integer number");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseErrorException(500);
        }
    }

}
