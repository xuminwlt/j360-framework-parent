package me.j360.framework.base.domain.rpc.result;

import lombok.Getter;
import me.j360.framework.base.constant.BaseErrorCode;
import me.j360.framework.base.domain.BaseDO;

public abstract class BaseResultSupport extends BaseDO {

    @Getter
    protected int code = BaseErrorCode.SUCCESS_CODE;

    @Getter
    protected String msg = "";

    @Getter
    protected boolean success = true;

    protected BaseResultSupport(boolean success,int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    public abstract static class Builder<R extends BaseResultSupport, B extends Builder<R, B>> {

        private B theBuilder;

        protected Boolean success;
        protected Integer code;
        protected String msg;

        public Builder () {
            theBuilder = getThis();
        }

        protected abstract B getThis();

        public B code(Integer code) {
            this.code = code;
            return theBuilder;
        }

        public B msg(String msg) {
            this.msg = msg;
            return theBuilder;
        }

        public B success(Boolean success) {
            this.success = success;
            return theBuilder;
        }

        public abstract R build();

    }


}
