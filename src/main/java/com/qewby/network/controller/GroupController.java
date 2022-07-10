package com.qewby.network.controller;

import java.util.List;

import com.qewby.network.annotation.RequestMapping;
import com.qewby.network.annotation.RequestMethod;
import com.qewby.network.annotation.RestController;
import com.qewby.network.dto.GroupDto;
import com.qewby.network.service.GroupService;
import com.qewby.network.service.implementation.DefaultGroupService;

@RestController
public class GroupController {

    private GroupService groupService = new DefaultGroupService();

    @RequestMapping(path = "/api/groups", method = RequestMethod.GET)
    public List<GroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }

}
