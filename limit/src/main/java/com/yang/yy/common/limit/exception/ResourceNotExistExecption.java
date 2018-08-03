package com.yang.yy.common.limit.exception;

public class ResourceNotExistExecption extends Exception {


    public ResourceNotExistExecption() {
    }

    public ResourceNotExistExecption(String message) {
        super(message);
    }

    public ResourceNotExistExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotExistExecption(Throwable cause) {
        super(cause);
    }

    public ResourceNotExistExecption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
