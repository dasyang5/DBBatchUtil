package com.alex.tool.db.converter;

import java.util.Set;

public class ConverterTool {

    /**
     * 获取插入语句
     * @param tableName 表名
     * @param keys 键值对
     * @return SQL
     */
    public static String getInsertSql(String tableName, Set<String> keys) {
        StringBuffer keysStr = new StringBuffer();
        StringBuffer paramsStr = new StringBuffer();
        for (String key : keys) {
            keysStr.append("," + key);
            paramsStr.append(",?");
        }
        return "INSERT INTO " + tableName + "(" + keysStr.toString().replaceFirst(",", "") +
                ") VALUES(" + paramsStr.toString().replaceFirst(",", "") + ")";
    }

    /**
     * 获取更新语句
     * @param tableName 表明
     * @param whereKeys WHERE字段
     * @param setKeys SET字段
     * @return SQL
     */
    public static String getUpdateSql(String tableName, Set<String> whereKeys, Set<String> setKeys) {
        StringBuffer setPart = new StringBuffer();
        StringBuffer wherePart = new StringBuffer();
        for (String key : setKeys) {
            setPart.append("," + key + "=?");
        }
        for (String key : whereKeys) {
            wherePart.append("AND " + key + "= ? ");
        }
        return "UPDATE " + tableName + " SET " + setPart.toString().replaceFirst(",", "") +
                " WHERE " + wherePart.toString().replaceFirst("AND", "");
    }

    /**
     * 获取删除语句
     * @param tableName 表明
     * @param primaryKeys   WHERE字段
     * @return SQL
     */
    public static String getDeleteSql(String tableName, Set<String> primaryKeys) {
        StringBuffer setPart = new StringBuffer();
        StringBuffer wherePart = new StringBuffer();
        for (String key : primaryKeys) {
            wherePart.append("AND " + key + "= ? ");
        }
        return "DELETE FROM  " + tableName +
                " WHERE " + wherePart.toString().replaceFirst("AND", "");
    }

}
