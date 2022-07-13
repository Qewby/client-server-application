package com.qewby.network.service;

import java.util.List;

import com.qewby.network.dto.GoodDto;
import com.qewby.network.dto.RequestGoodDto;

public interface GoodService {

    List<GoodDto> getAllGoods();

    GoodDto getGoodById(final String id);

    GoodDto createNewGood(final RequestGoodDto goodDto);

    public void updateGoodById(final String id, final RequestGoodDto goodDto);

    public void deleteGoodById(final String id);
}
