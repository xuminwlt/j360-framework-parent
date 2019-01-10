package me.j360.framework.base.domain.rpc.result;

import lombok.Getter;
import me.j360.framework.base.constant.BaseErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DefaultPageResult<D> extends BaseResultSupport {

    @Getter
    protected ListData<D> data;

    private DefaultPageResult(ListData<D> data, boolean success , int code, String msg) {
        super(success, code, msg);
        this.data = data;
    }

    private static Builder newBuilder() {
        return new Builder();
    }

    public static <D> DefaultPageResult success(Integer total, List<D> list) {
        return newBuilder().success(true).data(new ListData(total,list)).code(BaseErrorCode.SUCCESS_CODE).msg("").build();
    }

    public static <D> DefaultPageResult success(List<D> list) {
        list = null==list? new ArrayList<D>():list;
        return newBuilder().success(true).data(new ListData(list.size(),list)).code(BaseErrorCode.SUCCESS_CODE).msg("").build();
    }

    public static DefaultPageResult fail(int code, String msg) {
        return newBuilder().success(false).data(null).code(code).msg(msg).build();
    }

    public static <D> DefaultPageResult fail(Integer total, List<D> list, int code, String msg) {
        return newBuilder().success(false).data(new ListData(total,list)).code(code).msg(msg).build();
    }

    public static DefaultPageResult fail(BaseErrorCode errorCode) {
        return newBuilder().success(false).data(null).code(errorCode.getErrorCode()).msg(errorCode.getErrorMsg()).build();
    }

    public static class Builder<D> extends BaseResultSupport.Builder {

        private ListData<D> data;

        @Override
        protected Builder getThis() {
            return this;
        }

        public Builder data(ListData data) {
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

        @Override
        public DefaultPageResult build() {
            return new DefaultPageResult(data, success ,code, msg);
        }

    }

    @Override
    public String toString() {
        return String.format("DefaultResult( {}, {}, data= {}", code, msg, Objects.nonNull(data)?data:"data=null");
    }
}
