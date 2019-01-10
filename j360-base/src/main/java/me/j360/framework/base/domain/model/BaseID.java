package me.j360.framework.base.domain.model;

import me.j360.framework.base.domain.BaseDO;

public abstract class BaseID<ID extends Number> extends BaseDO {

    public abstract ID getId();
}
