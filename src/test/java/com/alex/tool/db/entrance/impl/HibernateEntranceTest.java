package com.alex.tool.db.entrance.impl;

import com.alex.tool.db.bean.TableOne;
import com.alex.tool.db.entrance.Entrance;
import org.junit.jupiter.api.Test;

import java.util.List;

class HibernateEntranceTest {

    private String url = "jdbc:mysql://192.168.73.138:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&characterSetResults=utf8&rewriteBatchedStatements=true&useSSL=false";
    private String username = "root";
    private String password = "123456";
    private String driverClassName = "com.mysql.cj.jdbc.Driver";//最新驱动的驱动名称

    private Entrance getEntranceInstance() {
        Entrance entrance = new HibernateEntrance(url, username, password, driverClassName);
        return entrance;
    }

    @Test
    void start() {
        getEntranceInstance().start().end();
    }

    @Test
    void end() {
        getEntranceInstance().start().insertObject(TableOne.getInstanceList()).end();
    }

    @Test
    void insertObject() {
        getEntranceInstance().start().insertObject(TableOne.getInstanceList()).end();
    }

    @Test
    void updateObject() {
        List<TableOne> list = TableOne.getInstanceList();
        for (TableOne o : list) {
            o.setColOne("222");
        }
        getEntranceInstance().start().updateObject(list, new String[]{"col_one"}, new String[]{"col_two"}).end();
    }


    @Test
    void deleteObject() {
        getEntranceInstance().start().deleteObject(TableOne.getInstanceList(), new String[]{"col_two"}).end();
    }

    @Test
    void executeSql() {
        getEntranceInstance().start().executeSql("1231123", null).end();
    }
}