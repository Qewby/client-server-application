package com.qewby.network.service;

import java.util.List;

import com.qewby.network.dto.GroupDto;


public interface GroupService
{
	List<GroupDto> getAllGroups();

	int addGroup(GroupDto groupDto);

	int updateGroup(GroupDto groupDto);

	int deleteGroupById(Integer id);

	GroupDto getGroupById(Integer id);

	GroupDto getGroupByName(String name);
}
