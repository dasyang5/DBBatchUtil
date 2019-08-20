package com.alex.tool.db.bean;

import java.util.List;

public class AnalystResult {

    public AnalystResult(String sql, List<Object[]> params) {
        this.sql = sql;
        this.params = params;
    }

    private String sql;

    private List<Object[]> params;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object[]> getParams() {
        return params;
    }

    public void setParams(List<Object[]> params) {
        this.params = params;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(sql + "\n");
        for (int i = 0; i < params.size(); i++) {
            for (int j = 0; j < params.get(i).length; j++) {
                sb.append(params.get(i)[j] + "  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
