package me.j360.framework.base.domain.rpc.query;

import lombok.Data;

@Data
public class DefaultQuery<ID> extends BaseQuery {

    private ID id;

}
