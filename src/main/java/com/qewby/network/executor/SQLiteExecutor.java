package com.qewby.network.executor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.qewby.network.dao.mapper.RowMapper;

public class SQLiteExecutor implements SQLExecutor {
    private static String databaseName = null;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void setDatabaseName(final String name) {
        databaseName = name;
    }

    private void checkDatabaseName() {
        if (databaseName == null) {
            System.out.println("No database name setted");
            System.exit(1);
        }
    }

    public <T> List<T> executeQuery(final String sql, final RowMapper<T> mapper)
            throws SQLException {
        checkDatabaseName();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            LinkedList<T> result = new LinkedList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(mapper.map(rs));
                }
            }
            return result;
        }
    }

    @Override
    public <T> List<T> executeQuery(String sql, List<Object> parameterList, RowMapper<T> mapper)
            throws SQLException {
        checkDatabaseName();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            for (int i = 1; i <= parameterList.size(); ++i) {
                statement.setObject(i, parameterList.get(i - 1));
            }
            LinkedList<T> result = new LinkedList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.add(mapper.map(rs));
                }
            }
            return result;
        }
    }

    @Override
    public int update(String sql) throws SQLException {
        checkDatabaseName();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            return statement.executeUpdate();
        }
    }

    @Override
    public int update(String sql, List<Object> parameterList) throws SQLException {
        checkDatabaseName();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            for (int i = 1; i <= parameterList.size(); ++i) {
                statement.setObject(i, parameterList.get(i - 1));
            }
            return statement.executeUpdate();
        }
    }

    @Override
    public void query(String sql) throws SQLException {
        checkDatabaseName();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.execute();
        }
    }

    @Override
    public void query(String sql, List<Object> parameterList) throws SQLException {
        checkDatabaseName();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
                PreparedStatement statement = connection.prepareStatement(sql);) {
            for (int i = 1; i <= parameterList.size(); ++i) {
                statement.setObject(i, parameterList.get(i - 1));
            }
            statement.execute();
        }
    }

}
