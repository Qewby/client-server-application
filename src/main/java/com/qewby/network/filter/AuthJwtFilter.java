package com.qewby.network.filter;

import java.io.IOException;
import java.util.List;

import com.qewby.network.dto.JWTTokenDto;
import com.qewby.network.dto.ResponseDto;
import com.qewby.network.service.UserService;
import com.qewby.network.service.implementation.DefaultUserService;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class AuthJwtFilter extends Filter {

    private static final String HEADER_NAME = "Authorization";

    private UserService userService = new DefaultUserService();

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        try {
            List<String> authHeader = exchange.getRequestHeaders().get(HEADER_NAME);
            if (authHeader == null || authHeader.isEmpty()) {
                ResponseSender.sendErrorMessage(exchange, 403, HEADER_NAME + " required");
                return;
            }
            String headerValue = authHeader.get(0);
            String[] values = headerValue.split("\\s+");
            if (values.length != 2) {
                ResponseSender.sendErrorMessage(exchange, 403, "Invalid " + HEADER_NAME + " header value");
                return;
            }
            String schema = values[0];
            String token = values[1];
            if (!schema.equals("Bearer")) {
                ResponseSender.sendErrorMessage(exchange, 403, "Invalid " + HEADER_NAME + " schema");
                return;
            }
            ResponseDto responseDto = userService.validateUserJwt(new JWTTokenDto(token));
            if (responseDto.getStatus() == 200) {
                chain.doFilter(exchange);
            } else {
                ResponseSender.sendResponse(exchange, responseDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseSender.sendErrorMessage(exchange, 500, "Internal server error");
            return;
        }
    }

    @Override
    public String description() {
        return "Check and validate jwt token";
    }

}
