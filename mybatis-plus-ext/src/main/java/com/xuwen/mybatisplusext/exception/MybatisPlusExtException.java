package com.xuwen.mybatisplusext.exception;

public class MybatisPlusExtException extends RuntimeException {

    public MybatisPlusExtException() {
    }

    public MybatisPlusExtException(String message) {
        super(message);
    }

    public MybatisPlusExtException(String message, Throwable cause) {
        super(message, cause);
    }

    public MybatisPlusExtException(Throwable cause) {
        super(cause);
    }

    public MybatisPlusExtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
