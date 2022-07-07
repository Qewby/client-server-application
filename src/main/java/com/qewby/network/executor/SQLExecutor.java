package com.qewby.network.executor;

import java.sql.SQLException;
import java.util.List;

import com.qewby.network.dao.RowMapper;

public interface SQLExecutor {
    <T> List<T> executeQuery(final String sql, final RowMapper<T> mapper)
            throws IllegalArgumentException, SQLException;

    <T> List<T> executeQuery(final String sql, final List<Object> parameterList, final RowMapper<T> mapper)
            throws IllegalArgumentException, SQLException;

    int update(final String sql) throws IllegalArgumentException, SQLException;

    int update(final String sql, final List<Object> parameterList) throws IllegalArgumentException, SQLException;

    void query(final String sql) throws IllegalArgumentException, SQLException;

    void query(final String sql, final List<Object> parameterList) throws IllegalArgumentException, SQLException;
}