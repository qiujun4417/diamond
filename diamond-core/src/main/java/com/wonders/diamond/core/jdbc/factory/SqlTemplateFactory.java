package com.wonders.diamond.core.jdbc.factory;

import com.wonders.diamond.core.exceptions.BaseException;
import com.wonders.diamond.core.jdbc.SqlTemplate;
import com.wonders.diamond.core.jdbc.SqlTemplateImpl;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by nick on 2016/12/30.
 * @author nick
 */
public class SqlTemplateFactory {

    private static ConcurrentHashMap<DataSource, SqlTemplate> HOLDER = new ConcurrentHashMap<DataSource, SqlTemplate>();

    public static SqlTemplate create(DataSource dataSource){
        if(dataSource==null)
            throw new BaseException(1000, "数据源为空, 请配置数据源");
        SqlTemplate sqlTemplate = HOLDER.get(dataSource);
        if(sqlTemplate==null){
            synchronized (SqlTemplate.class){
                sqlTemplate = HOLDER.get(dataSource);
                if(sqlTemplate!=null){
                    return sqlTemplate;
                }
                sqlTemplate = new SqlTemplateImpl(dataSource);
            }
        }
        return null;
    }
}
