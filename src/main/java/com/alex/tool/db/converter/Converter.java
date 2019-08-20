package com.alex.tool.db.converter;

import com.alex.tool.db.bean.AnalystResult;
import com.alex.tool.db.exception.DatabaseException;

import java.util.List;

/**
 * 负责将对象转换为SQL语句
 */
public interface Converter {

    /**
     * 将对象转为SQL插入语句
     *      空值跳过
     * @param list  操作的对象数组
     * @return  结果
     */
    AnalystResult convertForInsert(List<Object> list) throws DatabaseException;

    /**
     * 将对象转为SQL更新语句
     * @param list  操作的对象数组
     * @param setColumns    指定update语句SET部分字段
     * @param whereColumns  指定update语句WHERE部分字段
     * @return  结果
     */
    AnalystResult convertForUpdate(List<Object> list, String[] setColumns, String[] whereColumns) throws DatabaseException;


    /**
     * 将对象转为SQL删除语句
     * @param list  操作的对象数组
     * @param whereColumns  指定delete语句WHERE部分字段
     * @return  结果
     */
    AnalystResult convertForDelete(List<Object> list, String[] whereColumns) throws DatabaseException;

}
