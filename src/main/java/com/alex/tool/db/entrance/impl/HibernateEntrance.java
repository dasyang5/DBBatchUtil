package com.alex.tool.db.entrance.impl;

import com.alex.tool.db.bean.AnalystResult;
import com.alex.tool.db.bean.Configuration;
import com.alex.tool.db.converter.Converter;
import com.alex.tool.db.converter.impl.HibernateConverter;
import com.alex.tool.db.entrance.Entrance;
import com.alex.tool.db.exception.DatabaseException;
import com.alex.tool.db.execute.Execute;
import com.alex.tool.db.execute.impl.JDBCExecute;
import com.alex.tool.db.log.LogObserver;
import com.alex.tool.db.log.LogSubject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class HibernateEntrance implements Entrance {

    private Connection connection;

    /**
     * 日志主题
     *      存储日志接收类并且通知日志
     */
    private LogSubject logSubject = new LogSubject();

    /**
     * 指明底层使用JDBC执行
     */
    private Execute execute = new JDBCExecute();

    /**
     * 指明上层传入Hibernate对应的实体类
     */
    private Converter converter = new HibernateConverter();

    /**
     * 记录用户是否有调用start方法来初始化
     */
    private boolean isStart = false;

    /**
     * 批处理的执行参数
     */
    private Configuration configuration = new Configuration();

    public HibernateEntrance(Connection connection) {
        this.connection = connection;
    }

    public HibernateEntrance(String url, String username, String password, String driverClassName) {
        try {
            Class.forName(driverClassName);
            this.connection =  DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            logSubject.log("ERROR",e.getMessage()+":获取数据库连接失败！");
            logSubject.log("ERROR","url:"+url);
            logSubject.log("ERROR","username:"+username);
            logSubject.log("ERROR","password:"+password);
            logSubject.log("ERROR","driverClassName:"+driverClassName);
            throw new DatabaseException(e.getMessage(),"获取数据库连接失败！");
        }
    }

    @Override
    public Entrance start() {
        logSubject.log("INFO","开始初始化");
        if (connection == null) {
            logSubject.log("ERROR","CONNECTION_IS_NULL:数据库连接为空！");
            throw new DatabaseException("CONNECTION_IS_NULL","数据库连接为空！");
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            logSubject.log("ERROR",e.getMessage()+":关闭数据库自动提交错误！");
            throw new DatabaseException(e.getMessage(),"关闭数据库自动提交错误！");
        }
        isStart = true;
        return this;
    }

    @Override
    public Entrance end() {
        if (isStart) {
            try {
                connection.commit();
                connection.close();
                logSubject.log("INFO","执行结束！");
                logSubject.log("INFO","************************************************************");
            } catch (Exception e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                logSubject.log("ERROR",e.getMessage()+":释放资源失败！");
                throw new DatabaseException(e.getMessage(), "释放资源失败！");
            }
        } else {
            logSubject.log("ERROR","PROGRAM_NOT_START:未调用start()方法！");
            throw new DatabaseException("PROGRAM_NOT_START", "未调用start()方法！");
        }
        return this;
    }

    @Override
    public Entrance insertObject(List list) {
        return this.execute(converter.convertForInsert(list));
    }

    @Override
    public Entrance updateObject(List list, String[] setColumns, String[] whereColumns) {
        return this.execute(converter.convertForUpdate(list, setColumns, whereColumns));
    }

    @Override
    public Entrance deleteObject(List list, String[] whereColumns) {
        return this.execute(converter.convertForDelete(list, whereColumns));
    }

    @Override
    public Entrance executeSql(String sql, List<Object[]> params) {
        return this.execute(new AnalystResult(sql,params));
    }

    @Override
    public void registerLogObserver(LogObserver logObserver) {
        logSubject.registerObserver(logObserver);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    private Entrance execute(AnalystResult analystResult) {
        if (isStart) {
            if (analystResult.getSql() == null || analystResult.getSql().equals("")) {
                logSubject.log("ERROR","SQL_IS_NULL:SQL模板为空！");
                throw new DatabaseException("SQL_IS_NULL", "SQL模板为空！");
            }
            if (analystResult.getParams() != null && analystResult.getParams().size() > 0) {
                execute.batchExecuteSQL(analystResult, connection, configuration, logSubject);
            } else {
                logSubject.log("INFO","语句:"+analystResult.getSql()+"   共0条。");
            }
        } else {
            logSubject.log("ERROR","PROGRAM_NOT_START:未调用start()方法！");
            throw new DatabaseException("PROGRAM_NOT_START", "未调用start()方法！");
        }
        return this;
    }
}
