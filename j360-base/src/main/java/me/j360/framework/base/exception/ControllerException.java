package me.j360.framework.base.exception;

import me.j360.framework.base.constant.BaseErrorCode;

/**
 * @author: min_xu
 * @date: 2019/1/10 6:06 PM
 * 说明：
 */
public class ControllerException extends RuntimeException {

    private static final long serialVersionUID = -6438755184394143413L;

    protected int exceptionCode = -1;
    protected String message;
    private Object[] objs;

    public ControllerException(int exceptionCode, Object... objs) {
        this.objs = objs;
    }

    public int getExceptionCode() {
        return this.exceptionCode;
    }

    public ControllerException(int exceptionCode, String message) {
        super(message);
        this.message = message;
        this.exceptionCode = exceptionCode;
    }

    public ControllerException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.getErrorMsg());
        this.message = baseErrorCode.getErrorMsg();
        this.exceptionCode = baseErrorCode.getErrorCode();
    }

    public ControllerException(int exceptionCode, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.exceptionCode = exceptionCode;
    }

    public ControllerException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode.getErrorMsg(), cause);
        this.message = baseErrorCode.getErrorMsg();
        this.exceptionCode = baseErrorCode.getErrorCode();
    }

    public ControllerException(int exceptionCode, Throwable cause, Object... objs) {
        super(cause);
        this.objs = objs;
        this.message = cause.getMessage();
    }

    public Object[] getObjs() {
        return objs;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setExceptionCode(int exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
