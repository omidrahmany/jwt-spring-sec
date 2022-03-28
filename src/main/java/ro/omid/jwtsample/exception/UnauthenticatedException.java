package ro.omid.jwtsample.exception;

import ro.omid.jwtsample.util.ExceptionModel;

public class UnauthenticatedException extends RuntimeException{

    private ExceptionModel model;

    public UnauthenticatedException(ExceptionModel model) {
        this.model = model;
    }

    public UnauthenticatedException(String message, ExceptionModel model) {
        super(message);
        this.model = model;
    }

    public UnauthenticatedException(String message, Throwable cause, ExceptionModel model) {
        super(message, cause);
        this.model = model;
    }

    public UnauthenticatedException(Throwable cause, ExceptionModel model) {
        super(cause);
        this.model = model;
    }

    public UnauthenticatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ExceptionModel model) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.model = model;
    }
}
