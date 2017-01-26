package com.wonders.diamond.db;

import com.wonders.diamond.BaseSetup;
import com.wonders.diamond.core.jdbc.SqlTemplate;
import com.wonders.diamond.core.jdbc.builder.InsertSql;
import com.wonders.diamond.core.jdbc.factory.SqlTemplateFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;


/**
 * Created by ningyang on 2017/1/1.
 */

public class DBTest extends BaseSetup{

    @Test
    public void insertTest(){
        SqlTemplate sqlTemplate = SqlTemplateFactory.create(druidDataSource);
        InsertSql insertSql = new InsertSql(sqlTemplate);
        String studentId = uuid();
        String teacherId = "8a8195e959254287015925428f48000b";
        int result = insertSql.insert("tb_student").columns("id","age","class_name","student_name",
                "teacher_id").values(studentId, 25, "高三(4)班",
                "李老师",teacherId).doInsert();
        Assert.assertEquals("结果不正确", 1, result);
    }

    private String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
