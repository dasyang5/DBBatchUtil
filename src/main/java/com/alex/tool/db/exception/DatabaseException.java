package com.alex.tool.db.exception;

public class DatabaseException extends RuntimeException {

    private String type;

    private String msg;

    public DatabaseException(String type, String msg) {
        super(type + "\n" + msg + "\n");
        this.type = type;
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }
}
