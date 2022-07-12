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

    @Override
    public GroupDto addGroup(GroupDto groupDto) {
        try {
            if (groupDto.getName() == null) {
                throw new ResponseErrorException(409, "Group name is required");
            }
            if (groupDao.getGroupByName(groupDto.getName()).isPresent()) {
                throw new ResponseErrorException(409, "Group with such name already exists");
            }
            int rowCount = groupDao.insertNewGroup(groupDto);
            if (rowCount == 1) {
                return groupDao.getGroupByName(groupDto.getName()).orElseThrow();
            } else {
                throw new ResponseErrorException(500);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseErrorException(500);
        }
    }

    @Override
    public void updateGroup(final String stringId, final GroupDto groupDto) {
        try {
            final int id = Integer.valueOf(stringId);
            if (groupDto.getName() == null) {
                throw new ResponseErrorException(409, "Group name is required");
            }
            Optional<GroupDto> existed = groupDao.getGroupById(id);
            Optional<GroupDto> existedName = groupDao.getGroupByName(groupDto.getName());
            if (existed.isPresent()) {
                if (existedName.isPresent() && !existed.equals(existedName)) {
                    throw new ResponseErrorException(409, "Group name is already in use");
                }
                groupDto.setId(id);
                if (!groupDto.equals(existed.get())) {
                    groupDao.updateGroupInfo(groupDto);
                }
            } else {
                throw new ResponseErrorException(404, "Group with such id is not found");
            }
        } catch (NumberFormatException e) {
            throw new ResponseErrorException(400, "ID is not an integer number");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseErrorException(500);
        }
    }

    @Override
    public void deleteGroupById(final String stringId) {
        try {
            final int id = Integer.valueOf(stringId);
            int rowCount = groupDao.deleteGroupById(id);
            if (rowCount == 0) {
                throw new ResponseErrorException(404, "Group with such id is not found");
            }
        } catch (NumberFormatException e) {
            throw new ResponseErrorException(400, "ID is not an integer number");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ResponseErrorException(500);
        }
    }
}
