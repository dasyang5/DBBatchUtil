package com.alex.tool.db.analyze.impl;

import com.alex.tool.db.analyze.Analyze;
import com.alex.tool.db.exception.DatabaseException;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HibernateAnalyze implements Analyze {
    @Override
    public String getTableName(Object object) {
        Table tableAnnotation = object.getClass().getAnnotation(Table.class);
        if (tableAnnotation == null) {
            throw new DatabaseException("WRONG_OBJECT_TYPE", "传入的对象中无法获取表名！");
        }
        return tableAnnotation.name();
    }

    @Override
    public Map<String, Object> getPrimaryKeys(Object object) {
        Map<String, Object> map = new HashMap<>();
        Method[] methods = object.getClass().getMethods();
        Arrays.stream(methods).forEach(
                method -> {
                    Id id = method.getAnnotation(Id.class);
                    if (id != null) {
                        addObjectToMap(map, method, object);
                    }
                }
        );
        return map;
    }

    @Override
    public Map<String, Object> getNonPrimaryKeys(Object object) {
        Map<String, Object> map = new HashMap<>();
        Method[] methods = object.getClass().getMethods();
        Arrays.stream(methods).forEach(
                method -> {
                    Id id = method.getAnnotation(Id.class);
                    if (id == null) {
                        addObjectToMap(map, method, object);
                    }
                });
        return map;
    }

    @Override
    public Map<String, Object> getAllKeys(Object object) {
        Map<String, Object> map = new HashMap<>();
        Method[] methods = object.getClass().getMethods();
        Arrays.stream(methods).forEach(
                method -> addObjectToMap(map, method, object)
        );
        return map;
    }

    @Override
    public Map<String, Object> getSpecificKeys(Object object, String[] columns) {
        Map<String, Object> map = new HashMap<>();
        Method[] methods = object.getClass().getMethods();
        Arrays.stream(methods).forEach(
                method -> addObjectToMap(map, method, object, columns)
        );
        return map;
    }

    private void addObjectToMap(Map<String, Object> map, Method method, Object object) {
        Column column = method.getAnnotation(Column.class);
        if (column != null) {
            try {
                map.put(column.name(), method.invoke(object));
            } catch (Exception e) {
                throw new DatabaseException(e.getMessage(), "解析Hibernate实体错误");
            }
        }
    }

    private void addObjectToMap(Map<String, Object> map, Method method, Object object, String[] columns) {
        Column column = method.getAnnotation(Column.class);
        if (column != null) {
            for (String str : columns) {
                if (str.equals(column.name())) {
                    try {
                        map.put(column.name(), method.invoke(object));
                        return;
                    } catch (Exception e) {
                        throw new DatabaseException(e.getMessage(), "解析Hibernate实体错误");
                    }
                }
            }
        }
    }
}
