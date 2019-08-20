package com.alex.tool.db.bean;

/**
 * 批处理执行参数配置
 */
public class Configuration {

    /**
     * 批处理中每个批次能够处理的SQL条数
     *      决定开启的线程的多少
     */
    private int threadSize = 500;

    public int getThreadSize() {
        return threadSize;
    }

    public void setThreadSize(int threadSize) {
        this.threadSize = threadSize;
    }

}
