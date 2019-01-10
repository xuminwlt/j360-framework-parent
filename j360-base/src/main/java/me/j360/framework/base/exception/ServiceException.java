package me.j360.framework.base.exception;


import lombok.ToString;
import me.j360.framework.base.constant.BaseErrorCode;


@ToString
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -6438755184394143413L;

    protected int exceptionCode = -1;
    protected String message;

    public int getExceptionCode() {
        return this.exceptionCode;
    }

    public ServiceException(int exceptionCode,String message) {
        super(message);
        this.message = message;
        this.exceptionCode = exceptionCode;
    }

    public ServiceException(BaseErrorCode baseErrorCode) {
        super(baseErrorCode.getErrorMsg());
        this.message = baseErrorCode.getErrorMsg();
        this.exceptionCode = baseErrorCode.getErrorCode();
    }

    public ServiceException(int exceptionCode, String message, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.exceptionCode = exceptionCode;
    }

    public ServiceException(BaseErrorCode baseErrorCode, Throwable cause) {
        super(baseErrorCode.getErrorMsg(), cause);
        this.message = baseErrorCode.getErrorMsg();
        this.exceptionCode = baseErrorCode.getErrorCode();
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
