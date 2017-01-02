package com.wonders.diamond.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.wonders.diamond.core.jdbc.SqlTemplate;
import com.wonders.diamond.core.jdbc.builder.InsertSql;
import com.wonders.diamond.core.jdbc.factory.SqlTemplateFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.UUID;


/**
 * Created by ningyang on 2017/1/1.
 */

public class DBTest {

    DruidDataSource druidDataSource;

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

    @Test
    public void insertTest(){
        SqlTemplate sqlTemplate = SqlTemplateFactory.create(druidDataSource);
        InsertSql insertSql = new InsertSql(sqlTemplate);
        String studentId = uuid();
        String teacherId = "40288188591c6b6901591c6b6c5e0003";
        int result = insertSql.insert("tb_student").columns("id","age","class_name","student_name",
                "teacher_id").values(studentId, 25, "高三(4)班",
                "李老师",teacherId).doInsert();
        Assert.assertEquals("结果不正确", 1, result);
    }

    private String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
