package com.bs.excption;
import java.lang.Exception;
public class BizException extends Exception {
    private static final long serialVersionUID = 11451419198L;

    public BizException() {
        super();

    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }

    public BizException(String message, Throwable cause) {
        super(message, cause);

    }

    public BizException(String message) {
        super(message);

    }

    public BizException(Throwable cause) {
        super(cause);

    }



}
