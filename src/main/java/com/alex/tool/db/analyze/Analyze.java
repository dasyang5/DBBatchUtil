package com.alex.tool.db.analyze;

import com.alex.tool.db.exception.DatabaseException;

import java.util.Map;

/**
 * 负责解析对象，将对象转换为键值对
 */
public interface Analyze {

    /**
     * 获取实体对应的表名
     * @return
     */
    String getTableName(Object object) throws DatabaseException;

    /**
     * 获取主键字段
     * @return
     */
    Map<String,Object> getPrimaryKeys(Object object);

    /**
     * 获取非主键字段
     * @return
     */
    Map<String, Object> getNonPrimaryKeys(Object object);

    /**
     * 获取所有字段
     * @return
     */
    Map<String,Object> getAllKeys(Object object);

    /**
     * 获取部分字段的键值对
     * @param object
     * @param columns
     * @return
     */
    Map<String, Object> getSpecificKeys(Object object, String[] columns);


}
