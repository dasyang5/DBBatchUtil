package com.alex.tool.db.execute.impl;

import com.alex.tool.db.bean.AnalystResult;
import com.alex.tool.db.bean.Configuration;
import com.alex.tool.db.exception.DatabaseException;
import com.alex.tool.db.execute.Execute;
import com.alex.tool.db.log.LogSubject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JDBCExecute implements Execute {

    @Override
    public void batchExecuteSQL(AnalystResult analystResult, Connection connection, Configuration configuration, LogSubject logSubject) throws DatabaseException {

        logSubject.log("INFO", "语句:" + analystResult.getSql() + "   共" + analystResult.getParams().size() + "条。");

        int threadSize = configuration.getThreadSize();

        ExecutorService threadPool = null;

        PreparedStatement preparedStatement = null;

        try {
            int dataSize = analystResult.getParams().size();
            int threadNum = dataSize % threadSize == 0 ? dataSize / threadSize : dataSize / threadSize + 1;
            threadPool = Executors.newFixedThreadPool(threadNum);
            CountDownLatch countDownLatch = new CountDownLatch(threadNum);
            connection.setAutoCommit(false);
            List<Object[]> cutList;
            for (int i = 0; i < threadNum; i++) {
                if (i == threadNum - 1) {
                    cutList = analystResult.getParams().subList(threadSize * i, dataSize);
                } else {
                    cutList = analystResult.getParams().subList(threadSize * i, threadSize * (i + 1));
                }
                preparedStatement = connection.prepareStatement(analystResult.getSql());
                Future f = threadPool.submit(new JDBCExecuteThread(preparedStatement, cutList, countDownLatch), new String());
                f.get();
            }
            countDownLatch.await();
        } catch (Exception e) {
            try {
                connection.rollback();
                connection.close();
            } catch (SQLException e1) {
                logSubject.log("ERROR", e1.getMessage() + ":批处理执行错误！");
                throw new DatabaseException(e1.getMessage(), "批处理执行错误！");
            }
            logSubject.log("ERROR", e.getMessage() + ":批处理执行错误！");
            throw new DatabaseException(e.getMessage(), "批处理执行错误！");
        } finally {
            try {
                preparedStatement.close();
                threadPool.shutdown();
            } catch (SQLException e) {
                logSubject.log("ERROR", e.getMessage() + ":批处理执行错误！");
                throw new DatabaseException(e.getMessage(), "批处理执行错误！");
            }
        }
    }

}