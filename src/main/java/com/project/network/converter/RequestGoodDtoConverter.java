package com.qewby.network.converter;

import com.qewby.network.dto.GoodDto;
import com.qewby.network.dto.GroupDto;
import com.qewby.network.dto.RequestGoodDto;

public class RequestGoodDtoConverter {
    public static GoodDto convert(final RequestGoodDto requestGoodDto) {
        GoodDto good = new GoodDto();
        good.setId(requestGoodDto.getId());
        good.setName(requestGoodDto.getName());
        GroupDto groupDto = new GroupDto();
        groupDto.setId(Integer.valueOf(requestGoodDto.getGroupId()));
        good.setGroup(groupDto);
        good.setDescription(requestGoodDto.getDescription());
        good.setManufacturer(requestGoodDto.getManufacturer());
        good.setNumber(requestGoodDto.getNumber() == null ? 0 : requestGoodDto.getNumber());
        good.setPrice(requestGoodDto.getPrice());
        return good;
    }
}
