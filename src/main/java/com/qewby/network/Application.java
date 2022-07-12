package com.qewby.network;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.reflections.Reflections;

import com.sun.net.httpserver.*;
import com.qewby.network.annotation.RequestMapping;
import com.qewby.network.annotation.RestController;
import com.qewby.network.dto.UserDto;
import com.qewby.network.executor.SQLExecutor;
import com.qewby.network.executor.implementation.SQLiteExecutor;
import com.qewby.network.filter.Filter404;
import com.qewby.network.filter.AuthJwtFilter;
import com.qewby.network.filter.EmptyHandler;
import com.qewby.network.filter.RequestMappingFilter;
import com.qewby.network.filter.SetDefaultHeadersFilter;
import com.qewby.network.service.UserService;
import com.qewby.network.service.implementation.DefaultUserService;

public class Application {

    private HttpServer server;

    public Application() throws IOException {
        server = HttpServer.create();
    }

    public void initializeDatabase(String name) throws SQLException {
        String createGroupTableQuery = "CREATE TABLE IF NOT EXISTS `group` (\n" +
                "`group_id`	INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "`group_name`	TEXT NOT NULL UNIQUE,\n" +
                "`group_description`	TEXT)";

        String createGoodTableQuery = "CREATE TABLE IF NOT EXISTS `good` (\n" +
                "`good_id`	INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "`good_name`	TEXT NOT NULL UNIQUE,\n" +
                "`group_id`	INTEGER NOT NULL,\n" +
                "`good_description`	TEXT,\n" +
                "`manufacturer`	TEXT,\n" +
                "`number`	INTEGER NOT NULL,\n" +
                "`price`	NUMERIC NOT NULL,\n" +
                "FOREIGN KEY(`group_id`) REFERENCES `group`(`group_id`) ON UPDATE CASCADE ON DELETE CASCADE)";
        String createUserTableQuery = "CREATE TABLE IF NOT EXISTS `user` (\n" +
                "`login`    TEXT PRIMARY KEY,\n" +
                "`password`	TEXT NOT NULL)";

        SQLiteExecutor.setDatabaseName(name);
        SQLExecutor executor = new SQLiteExecutor();
        executor.update(createGroupTableQuery);
        executor.update(createGoodTableQuery);
        executor.update(createUserTableQuery);

        UserService userService = new DefaultUserService();
        try {
            userService.createNewUser(new UserDto("root", "root"));
        } catch (Exception e) {
        }
    }

    public void createContextes() {
        Map<String, HttpContext> contextMap = new HashMap<>();
        List<String> allowWithoutAuth = Arrays.asList("/login");

        HttpContext rootContext = server.createContext("/", new EmptyHandler());
        rootContext.getFilters().add(new SetDefaultHeadersFilter());
        rootContext.getFilters().add(new AuthJwtFilter());
        rootContext.getFilters().add(new Filter404());
        contextMap.put("/", rootContext);

        Reflections reflections = new Reflections(Application.class.getPackageName());
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(RestController.class);
        for (Class<?> clazz : classes) {
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    String path = method.getAnnotation(RequestMapping.class).path();
                    if (path.contains("{")) {
                        path = path.substring(0, path.indexOf('{'));
                    }
                    if (!contextMap.containsKey(path)) {
                        HttpContext context = server.createContext(path,
                                new EmptyHandler());
                        context.getFilters().add(new SetDefaultHeadersFilter());
                        if (!allowWithoutAuth.contains(path)) {
                            context.getFilters().add(new AuthJwtFilter());
                        }
                        contextMap.put(path, context);
                    }
                    contextMap.get(path).getFilters().add(new RequestMappingFilter(method));
                }
            }
            for (HttpContext context : contextMap.values()) {
                context.getFilters().add(new Filter404());
            }
        }
    }

    public void start(final int port) throws IOException {
        server.bind(new InetSocketAddress(8080), 0);
        System.out.println("Start listening at port " + server.getAddress().getPort());
        server.setExecutor(Executors.newFixedThreadPool(8));
        server.start();
    }

    public static void main(String[] args) throws SQLException, IOException {
        Application application = new Application();
        application.initializeDatabase("data.db");
        application.createContextes();
        application.start(8080);
    }
}
