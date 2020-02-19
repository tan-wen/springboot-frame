package com.aoyang.modules.book.service;

/**
 * 错误类型
 */
public enum ErrorCode {

    /**
     * 异常
     */
    ERROR_CODE(1),
    /**
     * 正常
     */
    NO_ERROR_CODE(0);




    private int value;

    ErrorCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
