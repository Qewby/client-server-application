package com.qewby.network.controller;

import java.util.List;

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
		if (groupService.addGroup(groupDto) == 1)
		{
			return groupDto;
		}
		return null;
	}

	@RequestMapping(path = "/api/group", method = RequestMethod.POST)
	public GroupDto updateGroup(@RequestBody GroupDto groupDto) {
		if (groupService.updateGroup(groupDto) == 1)
		{
			return groupDto;
		}
		return null;
	}

	@RequestMapping(path = "/api/group", method = RequestMethod.DELETE)
	public Integer deleteGroup(@RequestParameter("id") Integer groupId) {
		if (groupService.deleteGroupById(groupId) == 1)
		{
			return groupId;
		}
		return null;
	}
}
