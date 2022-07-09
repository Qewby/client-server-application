package com.qewby.network.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.qewby.network.annotation.PathParameter;
import com.qewby.network.annotation.RequestBody;
import com.qewby.network.annotation.RequestMapping;
import com.qewby.network.dto.ResponseDto;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class PathFilter extends Filter {
    private static final Gson gson = new Gson();

    private Method handler;

    public PathFilter(Method handler) {
        super();
        this.handler = handler;
    }

    private Map<String, String> comparePaths(String handlerPath, String requestPath) {
        String[] handlerPathArray = handlerPath.split("\\/");
        String[] requestPathArray = requestPath.split("\\/");
        if (handlerPathArray.length != requestPathArray.length || requestPath.endsWith("/")) {
            return null;
        }
        Map<String, String> parameterMap = new HashMap<>();
        for (int i = 0; i < handlerPathArray.length; ++i) {
            String path = handlerPathArray[i];
            if (path.startsWith("{") && path.endsWith("}")) {
                parameterMap.put(path.substring(1, path.length() - 1), requestPathArray[i]);
            } else if (!path.equals(requestPathArray[i])) {
                return null;
            }
        }
        return parameterMap;
    }

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        try {
            Map<String, String> pathParameters = comparePaths(
                    handler.getAnnotation(RequestMapping.class).path(),
                    exchange.getRequestURI().getPath().trim());
            if (pathParameters == null) {
                chain.doFilter(exchange);
            } else if (!handler.getAnnotation(RequestMapping.class).method().name()
                    .equals(exchange.getRequestMethod())) {
                chain.doFilter(exchange);
            } else {

                List<Object> handlerParameters = new LinkedList<>();
                Parameter[] parameters = handler.getParameters();
                for (Parameter param : parameters) {
                    PathParameter pathParameter = param.getAnnotation(PathParameter.class);
                    if (pathParameter != null) {
                        handlerParameters.add(pathParameters.get(pathParameter.value()));
                    }
                    RequestBody bodyParameter = param.getAnnotation(RequestBody.class);
                    if (bodyParameter != null) {
                        InputStream input = exchange.getRequestBody();
                        String body = new String(input.readAllBytes());
                        try {
                            handlerParameters.add(gson.fromJson(body, param.getType()));
                        } catch (JsonSyntaxException jsonSyntaxException) {
                            jsonSyntaxException.printStackTrace();
                            ErrorSender.sendErrorMessage(exchange, 400, "Bad body json syntax");
                        }
                    }
                }

                ResponseDto responseDto = null;
                Constructor<?> constructor;
                constructor = handler.getDeclaringClass().getConstructor();
                responseDto = (ResponseDto) handler.invoke(constructor.newInstance(), handlerParameters.toArray());

                String response = null;
                if (responseDto.getObject() != null) {
                    response = gson.toJson(responseDto.getObject());
                    exchange.sendResponseHeaders(responseDto.getStatus(), response.getBytes().length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                } else {
                    ErrorSender.sendErrorMessage(exchange, responseDto.getStatus(), responseDto.getErrorMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ErrorSender.sendErrorMessage(exchange, 500, "Internal server error");
        }
    }

    @Override
    public String description() {
        return "Filter checks if request path match method mapping and send response if match";
    }

}
