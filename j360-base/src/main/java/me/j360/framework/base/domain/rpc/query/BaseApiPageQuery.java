package me.j360.framework.base.domain.rpc.query;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public abstract class BaseApiPageQuery extends BaseQuery {

    /**
     * 自增的id,比较
     */
    protected Long id;

    protected int page = 0;
    protected int size = 30;

    protected int offset;

    public int getOffset() {
        if (!this.offsetReset) {
            return page==0?0:(page-1)*size;
        }
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
        this.offsetReset  = true;
    }

    private boolean offsetReset = false;
}
