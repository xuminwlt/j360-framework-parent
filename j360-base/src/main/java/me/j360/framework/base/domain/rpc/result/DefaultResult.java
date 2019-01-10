package me.j360.framework.base.domain.rpc.result;


import lombok.Getter;
import me.j360.framework.base.constant.BaseErrorCode;


public class DefaultResult<D> extends BaseResultSupport {

    @Getter
    protected D data;

    private static Builder newBuilder() {
        return new Builder();
    }

    public static DefaultResult success() {
        return newBuilder().success(true).data(null).code(BaseErrorCode.SUCCESS_CODE).msg("").build();
    }

    public static <D> DefaultResult success(D data) {
        return newBuilder().success(true).data(data).code(BaseErrorCode.SUCCESS_CODE).msg("").build();
    }

    public static DefaultResult fail(int code, String msg) {
        return newBuilder().success(false).data(null).code(code).msg(msg).build();
    }

    public static <D> DefaultResult fail(D data, int code, String msg) {
        return newBuilder().success(false).data(data).code(code).msg(msg).build();
    }

    public static DefaultResult fail(BaseErrorCode errorCode) {
        return newBuilder().success(false).data(null).code(errorCode.getErrorCode()).msg(errorCode.getErrorMsg()).build();
    }

    protected DefaultResult(D data, boolean success, int status, String error) {
        super(success, status, error);
        this.data = data;
    }

    public static class Builder<D> extends BaseResultSupport.Builder {

        private D data;

        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public DefaultResult build() {
            return new DefaultResult(data, success, code, msg);
        }

        public Builder data(D data) {
            this.data = data;
            return this;
        }

        @Override
        public Builder code(Integer code) {
            this.code = code;
            return this;
        }
        @Override
        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        @Override
        public Builder success(Boolean success) {
            this.success = success;
            return this;
        }
    }

    @Override
    public String toString() {
        return "DefaultResult(" + code + ", " + msg + ", " + null==data?"data=null":data.toString() + ")";
    }
}
