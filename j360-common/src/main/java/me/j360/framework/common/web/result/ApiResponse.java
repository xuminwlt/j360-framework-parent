package me.j360.framework.common.web.result;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.j360.framework.base.constant.BaseApiStatus;
import me.j360.framework.base.domain.web.BaseApiResponse;

@Slf4j
public class ApiResponse<D> extends BaseApiResponse {

    @Getter
    protected D data;

    public static Builder newBuilder() {
        return new Builder();
    }

    protected ApiResponse(D data, int status, String error) {
        super(status, error);
        this.data = data;
    }

    public static class Builder<D> extends BaseApiResponse.Builder {

        private D data;

        @Override
        protected Builder getThis() {
            return this;
        }

        @Override
        public ApiResponse build() {
            return new ApiResponse(data, status, error);
        }

        public Builder data(D data) {
            this.data = data;
            return this;
        }

        @Override
        public Builder status(Integer status) {
            this.status = status;
            return this;
        }
        @Override
        public Builder error(String error) {
            this.error = error;
            return this;
        }
    }

    public static ApiResponse success() {
        return newBuilder().data(null).status(BaseApiStatus.SUCCESS).error("").build();
    }

    public static <D> ApiResponse success(D data) {
        return newBuilder().data(data).status(BaseApiStatus.SUCCESS).error("").build();
    }


    public static ApiResponse fail(int code, String msg) {
        return newBuilder().data(null).status(code).error(msg).build();
    }

    public static <D> ApiResponse fail(D data, int code, String msg) {
        return newBuilder().data(data).status(code).error(msg).build();
    }

//    public static ApiResponse fail(ControllerException exception) {
//        Builder builder = newBuilder();
//        if (null != exception) {
//            builder.status(exception.getExceptionCode());
//            builder.error(StringUtils.isEmpty(exception.getMessage())?getMessage(String.valueOf(exception.getExceptionCode())):exception.getMessage());
//        }
//        return builder.data(null).build();
//    }
//    private static String getMessage(String code) {
//        return MessageSourceBundler.getMessage(code);
//    }
}
