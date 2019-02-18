package me.j360.framework.base.domain.model;

import lombok.Getter;
import lombok.Setter;

public class ValidationErrorData implements ErrorData {

    @Getter
    @Setter
    private Object code;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Object value;

    @Getter
    @Setter
    private String msg;
}
