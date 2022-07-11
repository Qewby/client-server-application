package com.qewby.network.service.implementation;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.qewby.network.dao.GroupDao;
import com.qewby.network.dao.implementation.DefaultGroupDao;
import com.qewby.network.dto.GroupDto;
import com.qewby.network.exception.ResponseErrorException;
import com.qewby.network.service.GroupService;


public class DefaultGroupService implements GroupService
{
	private GroupDao groupDao = new DefaultGroupDao();

	@Override
	public List<GroupDto> getAllGroups()
	{
		try
		{
			List<GroupDto> result = groupDao.getAllGroups();
			return result;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ResponseErrorException(500);
		}
	}

	@Override
	public int addGroup(final GroupDto groupDto)
	{
		try
		{
			return groupDao.insertNewGroup(groupDto);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ResponseErrorException(500);
		}
	}

	@Override
	public int updateGroup(final GroupDto groupDto)
	{
		try
		{
			return groupDao.updateGroupInfo(groupDto);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ResponseErrorException(500);
		}
	}

	@Override
	public int deleteGroupById(final Integer id)
	{
		try
		{
			 return groupDao.deleteGroupById(id);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ResponseErrorException(500);
		}
	}

	@Override
	public GroupDto getGroupById(final Integer id)
	{
		try
		{
			Optional<GroupDto> groupDto = groupDao.getGroupById(id);
			return groupDto.orElseThrow();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ResponseErrorException(500);
		}
	}

	@Override
	public GroupDto getGroupByName(final String name)
	{
		try
		{
			Optional<GroupDto> groupDto = groupDao.getGroupByName(name);
			return groupDto.orElseThrow();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new ResponseErrorException(500);
		}
	}
}
