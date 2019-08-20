package com.alex.tool.db.converter.impl;

import com.alex.tool.db.analyze.Analyze;
import com.alex.tool.db.analyze.impl.HibernateAnalyze;
import com.alex.tool.db.bean.AnalystResult;
import com.alex.tool.db.converter.Converter;
import com.alex.tool.db.converter.ConverterTool;
import com.alex.tool.db.exception.DatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 将Hibernate实体类转换为对应的处理语句结果集
 */
public class HibernateConverter implements Converter {

    Analyze analyze = new HibernateAnalyze();

    @Override
    public AnalystResult convertForInsert(List<Object> list) throws DatabaseException {
        if (list.size() > 0) {
            List<Object[]> paramsList = new ArrayList<>();
            for (Object o : list) {
                paramsList.add(analyze.getAllKeys(o).values().toArray());
            }
            return new AnalystResult(ConverterTool.getInsertSql(analyze.getTableName(list.get(0)),
                    analyze.getAllKeys(list.get(0)).keySet()),
                    paramsList);
        }
        return null;
    }

    @Override
    public AnalystResult convertForUpdate(List<Object> list, String[] setColumns, String[] whereColumns) throws DatabaseException {
        if (setColumns == null) {
            throw new DatabaseException("COLUMNS_IS_NULL", "SET字段不能为空！");
        } else if (setColumns.length == 0) {
            throw new DatabaseException("COLUMNS_IS_NULL", "SET字段数组没有元素！");
        } else if (whereColumns == null) {
            throw new DatabaseException("COLUMNS_IS_NULL", "WHERE字段不能为空！");
        } else if (whereColumns.length == 0) {
            throw new DatabaseException("COLUMNS_IS_NULL", "WHERE字段数组没有元素！");
        }
        if (list.size() > 0) {
            for (String str : setColumns) {
                if (!isInclude(list.get(0), str)) {
                    throw new DatabaseException("WRONG_COLUMN_NAME", str+"字段无法匹配！");
                }
            }
            for (String str : whereColumns) {
                if (!isInclude(list.get(0), str)) {
                    throw new DatabaseException("WRONG_COLUMN_NAME", str+"字段无法匹配！");
                }
            }
            List<Object[]> paramsList = new ArrayList<>();
            for (Object o : list) {
                List<Object> temp = new ArrayList<>();
                temp.addAll(analyze.getSpecificKeys(o, setColumns).values());
                temp.addAll(analyze.getSpecificKeys(o, whereColumns).values());
                paramsList.add(temp.toArray());
            }
            return new AnalystResult(ConverterTool.getUpdateSql(analyze.getTableName(list.get(0)),
                    analyze.getSpecificKeys(list.get(0), whereColumns).keySet(),
                    analyze.getSpecificKeys(list.get(0), setColumns).keySet()),
                    paramsList);
        }
        return null;
    }

    @Override
    public AnalystResult convertForDelete(List<Object> list, String[] whereColumns) throws DatabaseException {
        if (whereColumns == null) {
            throw new DatabaseException("COLUMNS_IS_NULL", "WHERE字段不能为空！");
        } else if (whereColumns.length == 0) {
            throw new DatabaseException("COLUMNS_IS_NULL", "WHERE字段数组没有元素！");
        }
        if (list.size() > 0) {
            for (String str : whereColumns) {
                if (!isInclude(list.get(0), str)) {
                    throw new DatabaseException("WRONG_COLUMN_NAME", str+"字段无法匹配！");
                }
            }
            List<Object[]> paramsList = new ArrayList<>();
            for (Object o : list) {
                paramsList.add(analyze.getSpecificKeys(o, whereColumns).values().toArray());
            }
            return new AnalystResult(ConverterTool.getDeleteSql(analyze.getTableName(list.get(0)),
                    analyze.getSpecificKeys(list.get(0), whereColumns).keySet()),
                    paramsList);
        }
        return null;
    }

    private boolean isInclude(Object o,String column) {
        Set<String> columns = analyze.getAllKeys(o).keySet();
        if (columns.contains(column)) {
            return true;
        }
        return false;
    }

}
