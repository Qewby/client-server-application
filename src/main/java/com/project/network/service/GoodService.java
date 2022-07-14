package com.project.network.service;

import java.util.List;

import com.project.network.dto.GoodDto;
import com.project.network.dto.RequestGoodDto;

public interface GoodService {

    List<GoodDto> getAllGoods();

    GoodDto getGoodById(final String id);

    GoodDto createNewGood(final RequestGoodDto goodDto);

    public void updateGoodById(final String id, final RequestGoodDto goodDto);

    public void deleteGoodById(final String id);
}
