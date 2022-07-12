package com.qewby.network.controller;

import java.util.List;

import com.qewby.network.annotation.PathParameter;
import com.qewby.network.annotation.RequestBody;
import com.qewby.network.annotation.RequestMapping;
import com.qewby.network.annotation.RequestMethod;
import com.qewby.network.annotation.RequestParameter;
import com.qewby.network.annotation.RestController;
import com.qewby.network.dto.GroupDto;
import com.qewby.network.service.GroupService;
import com.qewby.network.service.implementation.DefaultGroupService;
import com.sun.net.httpserver.Authenticator;


@RestController
public class GroupController {

    private GroupService groupService = new DefaultGroupService();

    @RequestMapping(path = "/api/groups", method = RequestMethod.GET)
    public List<GroupDto> getAllGroups() {
        return groupService.getAllGroups();
    }

    @RequestMapping(path = "/api/group", method = RequestMethod.PUT)
    public GroupDto addGroup(@RequestBody GroupDto groupDto) {
        return groupService.addGroup(groupDto);
    }

    @RequestMapping(path = "/api/group/{id}", method = RequestMethod.POST)
    public void updateGroup(@RequestBody GroupDto groupDto, @PathParameter("id") String groupId) {
        groupService.updateGroup(Integer.valueOf(groupId), groupDto);
    }

    @RequestMapping(path = "/api/group/{id}", method = RequestMethod.DELETE)
    public void deleteGroup(@PathParameter("id") String groupId) {
        groupService.deleteGroupById(Integer.valueOf(groupId));
    }
}
