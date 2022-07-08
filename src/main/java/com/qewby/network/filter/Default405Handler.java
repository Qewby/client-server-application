package com.qewby.network.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.qewby.network.dto.ErrorMessageDto;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Default405Handler implements HttpHandler {
    private static final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        int statusCode = 405;
        String message = "Invalid HTTP method";
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(statusCode, message,
                new Timestamp(System.currentTimeMillis()));
        String response = gson.toJson(errorMessageDto);
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
