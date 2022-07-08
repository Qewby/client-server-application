package com.qewby.network.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;

import com.google.gson.Gson;
import com.qewby.network.dto.ErrorMessageDto;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class GetFilter extends Filter {
    private static final Gson gson = new Gson();

    private Method handler;

    public GetFilter(Method handler) {
        super();
        this.handler = handler;
    }

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        if (exchange.getRequestMethod().equals("GET")) {
            int responseStatus = 200;
            Object responseObject = null;
            Constructor<?> constructor;
            try {
                constructor = handler.getDeclaringClass().getConstructor();
                responseObject = handler.invoke(constructor.newInstance());
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
            if (responseObject == null) {
                responseStatus = 500;
                String message = "Internal Server Error";
                responseObject = new ErrorMessageDto(responseStatus, message,
                        new Timestamp(System.currentTimeMillis()));
            }
            String response = gson.toJson(responseObject);
            exchange.sendResponseHeaders(responseStatus, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            chain.doFilter(exchange);
        }
    }

    @Override
    public String description() {
        return "Check GET method and execute controller method";
    }

}
