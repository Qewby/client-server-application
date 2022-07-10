package com.qewby.network.service;

import com.qewby.network.dto.GoodDto;

public interface GoodService {
    GoodDto getGoodById(final String id);

    GoodDto createNewGood(final GoodDto goodDto);

    public void updateGoodById(final String id, final GoodDto goodDto);

    public void deleteGoodById(String id);
}
