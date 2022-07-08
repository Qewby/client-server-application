package com.qewby.network.filter;

import java.io.IOException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class SetJsonFilter extends Filter {

    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        chain.doFilter(exchange);
    }

    @Override
    public String description() {
        return "Set Content-Type header to application/json";
    }

}
