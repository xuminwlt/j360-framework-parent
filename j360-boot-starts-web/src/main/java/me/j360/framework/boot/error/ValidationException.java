package me.j360.framework.boot.error;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException {


    private Object[] objs;

    private BindingResult bindingResult;


    public ValidationException(Object... objs) {
        this.objs = objs;
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }

    public ValidationException(Throwable cause, BindingResult bindingResult) {
        super(cause);
        this.bindingResult = bindingResult;
    }

    public ValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public final BindingResult getBindingResult() {
        return this.bindingResult;
    }

    public ValidationException(String message, Throwable cause, Object... objs) {
        super(message, cause);
        this.objs = objs;
    }

    public ValidationException(Throwable cause, Object... objs) {
        super(cause);
        this.objs = objs;
    }

    public Object[] getObjs() {
        return objs;
    }
}
