package com.qewby.network.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.qewby.network.dto.ErrorMessageDto;
import com.sun.net.httpserver.HttpExchange;

public class ErrorSender {
    private static final Gson gson = new Gson();

    public static void sendErrorMessage(HttpExchange exchange, int statusCode, String message) throws IOException {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(statusCode, message,
                new Timestamp(System.currentTimeMillis()));
        String response = gson.toJson(errorMessageDto);
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
