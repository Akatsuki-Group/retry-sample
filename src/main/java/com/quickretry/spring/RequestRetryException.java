package com.quickretry.spring;

/**
 * @author tian
 * @date 2021年05月31日 15:25
 */
public class RequestRetryException extends RuntimeException{
    public RequestRetryException() {
        super();
    }

    public RequestRetryException(String message) {
        super(message);
    }

    public RequestRetryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestRetryException(Throwable cause) {
        super(cause);
    }

    protected RequestRetryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
