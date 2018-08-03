package com.yang.yy.common.limit.exception;

public class ConfigInValidExecption extends Exception {


    public ConfigInValidExecption() {
    }

    public ConfigInValidExecption(String message) {
        super(message);
    }

    public ConfigInValidExecption(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigInValidExecption(Throwable cause) {
        super(cause);
    }

    public ConfigInValidExecption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
