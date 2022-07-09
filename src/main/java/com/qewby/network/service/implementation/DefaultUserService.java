package com.qewby.network.service.implementation;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import com.qewby.network.dao.UserDao;
import com.qewby.network.dao.implementation.DefaultUserDao;
import com.qewby.network.dto.JWTTokenDto;
import com.qewby.network.dto.ResponseDto;
import com.qewby.network.dto.UserDto;
import com.qewby.network.security.AuthenticationUtils;
import com.qewby.network.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;

public class DefaultUserService implements UserService {

    private static final int JWT_EXPIRATION_HOURS = 4;

    UserDao userDao = new DefaultUserDao();

    @Override
    public ResponseDto loginUserAndReturnJwt(UserDto credentials) {
        ResponseDto responseDto = new ResponseDto();
        try {
            Optional<UserDto> userData = userDao.getUserByLogin(credentials.getLogin());
            if (userData.isPresent()) {
                String originalPassword = credentials.getPassword();
                String storedPassword = userData.get().getPassword();
                if (AuthenticationUtils.validatePassword(originalPassword, storedPassword)) {
                    SecretKey key = AuthenticationUtils.getKey();
                    String jwt = Jwts.builder()
                            .claim("login", credentials.getLogin())
                            .setIssuedAt(new Date())
                            .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(JWT_EXPIRATION_HOURS)))
                            .signWith(key)
                            .compact();
                    responseDto.setObject(new JWTTokenDto(jwt));
                    responseDto.setStatus(200);
                } else {
                    responseDto.setStatus(401);
                    responseDto.setErrorMessage("Invalid credentials");
                }
            } else {
                responseDto.setStatus(401);
                responseDto.setErrorMessage("No such user");
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setStatus(500);
            responseDto.setErrorMessage("Internal server error");
        }
        return responseDto;
    }

    @Override
    public ResponseDto createNewUser(final UserDto userDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            if (userDao.getUserByLogin(userDto.getLogin()).isPresent()) {
                responseDto.setStatus(409);
                responseDto.setErrorMessage("User with such login already exists");
            } else {
                userDto.setPassword(AuthenticationUtils.generatePasswordHash(userDto.getPassword(), 10));
                int rowCount = userDao.insertNewUser(userDto);
                if (rowCount == 1) {
                    responseDto.setObject(userDto);
                    responseDto.setStatus(201);
                } else {
                    throw new SQLException();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setStatus(500);
            responseDto.setErrorMessage("Internal server error");
        }
        return responseDto;
    }

    @Override
    public ResponseDto validateUserJwt(final JWTTokenDto jwtTokenDto) {
        ResponseDto responseDto = new ResponseDto();
        try {
            SecretKey key = AuthenticationUtils.getKey();
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtTokenDto.getToken());
            responseDto.setObject(jwtTokenDto);
            responseDto.setStatus(200);
        } catch (SignatureException e) {
            responseDto.setStatus(403);
            responseDto.setErrorMessage("Invalid token");
        } catch (Exception e) {
            e.printStackTrace();
            responseDto.setStatus(500);
            responseDto.setErrorMessage("Internal server error");
        }
        return responseDto;
    }
}
