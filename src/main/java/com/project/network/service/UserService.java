package com.qewby.network.service;

import com.qewby.network.dto.JwtTokenDto;
import com.qewby.network.dto.UserDto;

public interface UserService {
    JwtTokenDto loginUserAndReturnJwt(final UserDto credentials);

    UserDto createNewUser(final UserDto userDto);

    boolean validateUserJwt(final JwtTokenDto jwtTokenDto);
}
