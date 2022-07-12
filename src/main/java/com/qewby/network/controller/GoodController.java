package com.qewby.network.controller;

import java.util.List;

import com.qewby.network.annotation.PathParameter;
import com.qewby.network.annotation.RequestBody;
import com.qewby.network.annotation.RequestMapping;
import com.qewby.network.annotation.RequestMethod;
import com.qewby.network.annotation.RestController;
import com.qewby.network.dto.GoodDto;
import com.qewby.network.dto.RequestGoodDto;
import com.qewby.network.service.GoodService;
import com.qewby.network.service.implementation.DefaultGoodService;

import java.sql.SQLException;

@RestController
public class GoodController {

    private GoodService goodService = new DefaultGoodService();

    @RequestMapping(path = "/api/goods", method = RequestMethod.GET)
    public List<GoodDto> getGoodById() {
        return goodService.getAllGoods();
    }

    @RequestMapping(path = "/api/good/{id}", method = RequestMethod.GET)
    public GoodDto getGoodById(@PathParameter("id") String id) {
        return goodService.getGoodById(id);
    }

    @RequestMapping(path = "/api/good", method = RequestMethod.PUT)
    public GoodDto createNewGood(@RequestBody RequestGoodDto requestGoodDto) {
        return goodService.createNewGood(requestGoodDto);
    }

    @RequestMapping(path = "/api/good/{id}", method = RequestMethod.POST)
    public void updateGoodById(@PathParameter("id") String id,
            @RequestBody RequestGoodDto requestGoodDto) {
        goodService.updateGoodById(id, requestGoodDto);
    }

    @RequestMapping(path = "/api/good/{id}", method = RequestMethod.DELETE)
    public void deleteGoodById(@PathParameter("id") String id) {
        goodService.deleteGoodById(id);
    }
}
