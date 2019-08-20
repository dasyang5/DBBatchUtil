package com.alex.tool.db.execute.impl;

import com.alex.tool.db.exception.DatabaseException;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class JDBCExecuteThread implements Runnable{

    private PreparedStatement preparedStatement;
    private List<Object[]> cutList;
    private CountDownLatch countDownLatch;

    public JDBCExecuteThread(PreparedStatement preparedStatement, List<Object[]> cutList, CountDownLatch countDownLatch) {
        this.preparedStatement = preparedStatement;
        this.cutList = cutList;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            if (cutList != null && cutList.size() > 0) {
                for (Object[] obj : cutList) {
                    for (int i = 0; i < obj.length; i++) {
                        preparedStatement.setObject(i + 1,  obj[i]);
                    }
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
            preparedStatement.close();
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage(),"批处理执行时子线程发生错误！");
        } finally {
            countDownLatch.countDown();
        }
    }
}
