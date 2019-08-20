package com.alex.tool.db.entrance;

import com.alex.tool.db.bean.Configuration;
import com.alex.tool.db.log.LogObserver;

import java.util.List;

/**
 * 入口
 */
public interface Entrance {

    /**
     * 初始化Entrance
     *      所有的初始化操作都可以在这个方法进行
     * @return Entrance
     */
    Entrance start();

    /**
     * 释放资源，并且提交事务
     * @return Entrance
     */
    Entrance end();

    /**
     * 批量插入
     * @param list 需要插入的对象数组
     * @return Entrance
     */
    Entrance insertObject(List list);

    /**
     * 批量更新
     * @param list 需要更新的对象数组
     * @param setColumns 指明update语句的set部分使用哪些字段
     * @param whereColumns 指明update语句的where部分使用哪些字段
     * @return Entrance
     */
    Entrance updateObject(List list, String[] setColumns, String[] whereColumns);

    /**
     * 批量删除
     * @param list 需要删除的对象数组
     * @param whereColumns 指明delete语句的where部分使用哪些字段
     * @return Entrance
     */
    Entrance deleteObject(List list, String[] whereColumns);

    /**
     * 批量执行SQL
     * @param sql  带占位符的SQL语句
     * @param params    参数
     * @return  Entrance
     */
    Entrance executeSql(String sql, List<Object[]> params);

    /**
     * 添加日志观察者
     * @param logObserver
     */
    void registerLogObserver(LogObserver logObserver);

    /**
     * 获取批处理的执行配置
     * @return
     */
    Configuration getConfiguration();

}
