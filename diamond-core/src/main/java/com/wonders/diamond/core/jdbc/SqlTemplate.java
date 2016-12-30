package com.wonders.diamond.core.jdbc;

import com.wonders.diamond.core.jdbc.dbutils.ResultSetHandler;

import java.sql.SQLException;

/**
 * Created by nick on 2016/12/30.
 */
public interface SqlTemplate {

    void createTable(final String sql) throws SQLException;

    int[] batchInsert(final String sql, final Object[][] params) throws SQLException;

    int[] batchUpdate(final String sql, final Object[][] params) throws SQLException;

    int insert(final String sql, final Object... params) throws SQLException;

    int update(final String sql, final Object... params) throws SQLException;

    int delete(final String sql, final Object... params) throws SQLException;

    <T> T query(final String sql, final ResultSetHandler<T> rsh, final Object... params) throws SQLException;

    <T> T queryForValue(final String sql, final Object... params) throws SQLException;

    <T> T executeInTransaction(SqlExecutor<T> executor);

    void executeInTransaction(SqlExecutorVoid executor);
}
