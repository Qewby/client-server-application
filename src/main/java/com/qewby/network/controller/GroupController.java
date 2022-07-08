package com.qewby.network.controller;

import java.util.List;

import com.qewby.network.annotation.GetMapping;
import com.qewby.network.annotation.RestController;
import com.qewby.network.dto.GroupDto;
import com.qewby.network.service.GroupService;
import com.qewby.network.service.implementation.DefaultGroupService;

@RestController
public class GroupController {

    private GroupService groupService = new DefaultGroupService();

    @GetMapping("/groups")
    public List<GroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }
}
