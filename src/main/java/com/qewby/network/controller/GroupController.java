package com.qewby.network.controller;

import com.qewby.network.annotation.PathParameter;
import com.qewby.network.annotation.RequestBody;
import com.qewby.network.annotation.RequestMapping;
import com.qewby.network.annotation.RequestMethod;
import com.qewby.network.annotation.RequestParameter;
import com.qewby.network.annotation.RestController;
import com.qewby.network.dto.GroupDto;
import com.qewby.network.dto.ResponseDto;
import com.qewby.network.service.GroupService;
import com.qewby.network.service.implementation.DefaultGroupService;

@RestController
public class GroupController {

    private GroupService groupService = new DefaultGroupService();

    @RequestMapping(path = "/api/groups", method = RequestMethod.GET)
    public ResponseDto getAllGroups() {
        return groupService.getAllGroups();
    }

    @RequestMapping(path = "/api/group/{id}", method = RequestMethod.GET)
    public ResponseDto getGroupById(@PathParameter("id") String id) {
        GroupDto groupDto = new GroupDto(Integer.valueOf(id), "Овочі", null);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setObject(groupDto);
        responseDto.setStatus(200);
        return responseDto;
    }

    @RequestMapping(path = "/api/group/{id}", method = RequestMethod.POST)
    public ResponseDto updateGroupById(@PathParameter("id") String id, @RequestBody GroupDto group, @RequestParameter("name") String name) {
        System.out.println(id);
        if (group != null) {
            System.out.println(group.toString());
        } else {
            System.out.println("NULL");
        }
        if (name != null) {
            System.out.println(name);
        } else {
            System.out.println("NULL");
        }

        GroupDto groupDto = new GroupDto(Integer.valueOf(id), "Овочі", null);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setObject(groupDto);
        responseDto.setStatus(200);
        return responseDto;
    }

}