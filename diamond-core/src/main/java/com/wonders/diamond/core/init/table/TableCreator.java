package com.wonders.diamond.core.init.table;

import com.wonders.diamond.core.jdbc.SqlTemplate;
import com.wonders.diamond.core.jdbc.factory.SqlTemplateFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by nick on 2017/1/25.
 */
public class TableCreator {

    public static void tableInit(DataSource dataSource) throws SQLException {
        String createTableA = "CREATE TABLE IF NOT EXISTS tb_diamond_app(" +
                "appId varchar(32) not null primary key, \n" +
                "appName varchar(40) NOT NULL, \n" +
                "createTime datetime NOT NULL default NOW(), \n" +
                "updateTime datetime NOT NULL default NOW())";
        String createTableB = "CREATE TABLE IF NOT EXISTS tb_diamond_configuration(" +
                "id varchar(32) not null primary key, \n" +
                "appId varchar(32) not null, \n" +
                "profile varchar(4) not null, \n" +
                "configKey varchar(100) not null, \n" +
                "createTime datetime NOT NULL default NOW(), \n" +
                "updateTime datetime NOT NULL default NOW())";
        SqlTemplate sqlTemplate = SqlTemplateFactory.create(dataSource);
        sqlTemplate.createTable(createTableA);
        sqlTemplate.createTable(createTableB);
    }
}
