package com.alex.tool.db.execute;

import com.alex.tool.db.bean.AnalystResult;
import com.alex.tool.db.bean.Configuration;
import com.alex.tool.db.exception.DatabaseException;
import com.alex.tool.db.log.LogSubject;


import java.sql.Connection;

public interface Execute {

    /**
     * 批处理执行SQL语句
     *
     * @param analystResult 执行的语句
     * @param connection    数据库连接
     * @param configuration    批处理配置
     * @param logSubject    日志主题,使用观察者模式实现日志通知，用户可以加入自己的日志类来接收日志
     * @throws DatabaseException 自定义异常
     */
    void batchExecuteSQL(AnalystResult analystResult, Connection connection, Configuration configuration, LogSubject logSubject) throws DatabaseException;

}
