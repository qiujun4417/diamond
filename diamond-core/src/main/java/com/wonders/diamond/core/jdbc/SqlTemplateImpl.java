package com.wonders.diamond.core.jdbc;

import com.wonders.diamond.core.exceptions.BaseException;
import com.wonders.diamond.core.jdbc.dbutils.DbRunner;
import com.wonders.diamond.core.jdbc.dbutils.ResultSetHandler;
import com.wonders.diamond.core.jdbc.dbutils.ScalarHandler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by nick on 2016/12/30.
 * @author nick
 */
public class SqlTemplateImpl implements SqlTemplate{

    private final DataSource dataSource;
    private final DbRunner dbRunner = new DbRunner();

    public SqlTemplateImpl(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void createTable(String sql) throws SQLException {
        update(sql);
    }

    @Override
    public int[] batchInsert(String sql, Object[][] params) throws SQLException {
        return batchUpdate(sql, params);
    }

    @Override
    public int[] batchUpdate(String sql, Object[][] params) throws SQLException {
        return execute(false, conn -> batchUpdate(conn, sql, params));
    }

    @Override
    public int insert(String sql, Object... params) throws SQLException {
        return update(sql, params);
    }

    public int update(final Connection conn, final String sql, final Object... params) throws SQLException {
        return dbRunner.update(conn, sql, params);
    }

    public int update(final String sql, final Object... params) throws SQLException {
        return execute(false, conn -> update(conn, sql, params));
    }

    @Override
    public int delete(String sql, Object... params) throws SQLException {
        return update(sql, params);
    }

    @Override
    public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) throws SQLException {
        return execute(true, conn -> query(conn, sql, rsh, params));
    }

    private <T> T query(final Connection conn, final String sql, final ResultSetHandler<T> rsh, final Object... params) throws SQLException{
        return dbRunner.query(conn, sql, rsh, params);
    }

    @Override
    public <T> T queryForValue(String sql, Object... params) throws SQLException {
        return query(sql, new ScalarHandler<T>(), params);
    }

    @Override
    public <T> T executeInTransaction(SqlExecutor<T> executor) {
        Connection conn = null;
        try {
            conn = TxConnectionFactory.getTxConnection(dataSource);
            T res = executor.run(conn);
            conn.commit();
            return res;
        } catch (Error e) {
            throw rollback(conn, e);
        } catch (Exception e) {
            throw rollback(conn, e);
        } finally {
            TxConnectionFactory.closeTx(conn);
        }
    }

    @Override
    public void executeInTransaction(SqlExecutorVoid executor) {
        executeInTransaction(getWrapperExecutor(executor));
    }

    private SqlExecutor<Void> getWrapperExecutor(final SqlExecutorVoid voidExecutor) {
        return conn -> { voidExecutor.run(conn);
                         return null;
        };
    }


    private int[] batchUpdate(final Connection conn, final String sql, final Object[][] params) throws SQLException {
        return dbRunner.batch(conn, sql, params);
    }

    private <T> T execute(boolean isReadOnly, SqlExecutor<T> executor) throws SQLException {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            if (isReadOnly) {
                conn.setReadOnly(true);
            }
            return executor.run(conn);
        } finally {
            close(conn);
        }
    }

    private void close(Connection conn) throws SQLException {
        if (conn != null) {
            if (conn.isReadOnly()) {
                conn.setReadOnly(false);  // restore NOT readOnly before return to pool
            }
            conn.close();
        }
    }

    private BaseException rollback(Connection conn, Throwable e) {
        try {
            if (conn != null) {
                conn.rollback();
            }
            return new BaseException(e);
        } catch (SQLException se) {
            return new BaseException("Unable to rollback transaction", e);
        }
    }
}
