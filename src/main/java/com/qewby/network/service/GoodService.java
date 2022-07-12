package com.qewby.network.service;

import java.util.List;

import com.qewby.network.dto.GoodDto;

public interface GoodService {

    List<GoodDto> getAllGoods();

    GoodDto getGoodById(final String id);

    GoodDto createNewGood(final GoodDto goodDto);

    public void updateGoodById(final String id, final GoodDto goodDto);

    public void deleteGoodById(String id);
}
