package com.qewby.network.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Map;

import com.google.gson.Gson;
import com.qewby.network.dto.ErrorMessageDto;
import com.qewby.network.dto.ResponseDto;
import com.sun.net.httpserver.HttpExchange;

public class ResponseSender {
    private static final Gson gson = new Gson();

    private static void writeHeaders(HttpExchange exchange, Map<String, String> headers) {
        if (headers != null) {
            for (String key : headers.keySet()) {
                exchange.getResponseHeaders().add(key, headers.get(key));
            }
        }
    }

    public static void sendErrorMessage(HttpExchange exchange, int statusCode, String message) throws IOException {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(statusCode, message,
                new Timestamp(System.currentTimeMillis()));
        String response = gson.toJson(errorMessageDto);
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    public static void sendResponse(HttpExchange exchange, ResponseDto responseDto) throws IOException {
        String response = null;
        writeHeaders(exchange, responseDto.getHeaders());
        if (responseDto.getObject() != null) {
            response = gson.toJson(responseDto.getObject());
            exchange.sendResponseHeaders(responseDto.getStatus(), response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            sendErrorMessage(exchange, responseDto.getStatus(), responseDto.getErrorMessage());
            return;
        }
    }
}
