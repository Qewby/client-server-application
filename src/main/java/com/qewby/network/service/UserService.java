package com.qewby.network.service;

import com.qewby.network.dto.JWTTokenDto;
import com.qewby.network.dto.ResponseDto;
import com.qewby.network.dto.UserDto;

public interface UserService {
    ResponseDto loginUserAndReturnJwt(final UserDto credentials);

    ResponseDto createNewUser(final UserDto userDto);

    ResponseDto validateUserJwt(final JWTTokenDto jwtTokenDto);
}
