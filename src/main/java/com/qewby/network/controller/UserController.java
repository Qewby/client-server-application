package com.qewby.network.controller;

import com.qewby.network.annotation.RequestBody;
import com.qewby.network.annotation.RequestMapping;
import com.qewby.network.annotation.RequestMethod;
import com.qewby.network.annotation.RestController;
import com.qewby.network.dto.ResponseDto;
import com.qewby.network.dto.UserDto;
import com.qewby.network.service.UserService;
import com.qewby.network.service.implementation.DefaultUserService;

@RestController
public class UserController {

    UserService userService = new DefaultUserService();

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseDto loginUser(@RequestBody UserDto credentials) {
        return userService.loginUserAndReturnJwt(credentials);
    }
}
