package me.j360.framework.base.domain.model;

import lombok.Getter;
import lombok.Setter;

public abstract class BaseModel<ID> extends BaseID {

    @Getter
    @Setter
    private Integer version;

    @Getter
    @Setter
    private Long createTime;

    @Getter
    @Setter
    private Long updateTime;

}
