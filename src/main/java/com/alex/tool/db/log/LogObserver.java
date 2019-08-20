package com.alex.tool.db.log;

public interface LogObserver {

    /**
     * 日志接收方法
     *      获取日志
     * @param type
     * @param msg
     */
    void log(String type,String msg);
}
