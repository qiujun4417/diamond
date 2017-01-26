package com.wonders.diamond;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Before;

import java.sql.SQLException;

/**
 * Created by nick on 2017/1/26.
 */
public class BaseSetup {

    public DruidDataSource druidDataSource;

    @Before
    public void setup() throws SQLException {
        druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF8&autoReconnect=true&rewriteBatchedStatements=TRUE&zeroDateTimeBehavior=convertToNull&useSSL=false");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("3202547c");
        druidDataSource.setFilters("stat");
        druidDataSource.setInitialSize(10);
        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(200);
        druidDataSource.setMaxWait(60000L);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000L);
        druidDataSource.setMinEvictableIdleTimeMillis(300000L);
        druidDataSource.setValidationQuery("SELECT 1");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(true);
        druidDataSource.setTestOnReturn(false);
    }

}
