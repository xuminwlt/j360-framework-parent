package me.j360.framework.base.domain.web;

import lombok.Getter;
import me.j360.framework.base.constant.BaseApiStatus;
import me.j360.framework.base.domain.BaseDO;

/**
 * 说明：
 */
public abstract class BaseApiResponse extends BaseDO {

    @Getter
    protected int status = BaseApiStatus.SUCCESS;

    @Getter
    protected String error = "";

    protected BaseApiResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public abstract static class Builder<R extends BaseApiResponse, B extends Builder<R, B>> {

        private B theBuilder;

        protected Integer status;
        protected String error;

        public Builder () {
            theBuilder = getThis();
        }

        protected abstract B getThis();

        public B status(Integer status) {
            this.status = status;
            return theBuilder;
        }

        public B error(String error) {
            this.error = error;
            return theBuilder;
        }

        public abstract R build();

    }


}
