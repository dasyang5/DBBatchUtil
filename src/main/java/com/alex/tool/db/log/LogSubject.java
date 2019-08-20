package com.alex.tool.db.log;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志主题类
 *      使用观察者模式设计工具包中日志的输出
 */
public class LogSubject {

    /**
     * 订阅者列表
     */
    private List<LogObserver> logObserverArrayList = new ArrayList<>();

    /**
     * 注册订阅者
     * @param logObserver 用户自定义的日志接收类
     */
    public void registerObserver(LogObserver logObserver) {
        if (logObserver != null) {
            logObserverArrayList.add(logObserver);
        }
    }

    /**
     * 通知日志信息到所有的订阅者
     * @param type 日志类型
     * @param msg 日志信息
     */
    public void log(String type,String msg) {
        for (LogObserver o : logObserverArrayList) {
            o.log(type,msg);
        }
    }
}
