package com.qewby.network.controller;

import com.qewby.network.annotation.PathParameter;
import com.qewby.network.annotation.RequestBody;
import com.qewby.network.annotation.RequestMapping;
import com.qewby.network.annotation.RequestMethod;
import com.qewby.network.annotation.RestController;
import com.qewby.network.dto.GoodDto;
import com.qewby.network.dto.GoodGroupDto;
import com.qewby.network.service.GoodService;
import com.qewby.network.service.implementation.DefaultGoodService;

import java.sql.SQLException;

@RestController
public class GoodController {

    private GoodService goodService = new DefaultGoodService();

    @RequestMapping(path = "/api/good/{id}", method = RequestMethod.GET)
    public GoodDto getGoodById(@PathParameter("id") String id) {
        return goodService.getGoodById(id);
    }

    @RequestMapping(path = "/api/good", method = RequestMethod.PUT)
    public void createNewGood(@RequestBody GoodGroupDto goodDto) throws SQLException {
        goodService.createNewGood(goodDto);
    }

    @RequestMapping(path = "/api/good/{id}", method = RequestMethod.POST)
    public void updateGoodById(@PathParameter("id") String id,
            @RequestBody GoodGroupDto goodDto) {
        goodService.updateGoodById(id, goodDto);
    }

    @RequestMapping(path = "/api/good/{id}", method = RequestMethod.DELETE)
    public void deleteGoodById(@PathParameter("id") String id) {
        goodService.deleteGoodById(id);
    }
}
