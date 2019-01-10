package me.j360.framework.base.domain.rpc.result;


import lombok.Data;
import lombok.NoArgsConstructor;
import me.j360.framework.base.domain.BaseDO;

import java.util.List;

@Data
@NoArgsConstructor
public class ListData<D> extends BaseDO {


    private Integer total;

    private List<D> list;

    public ListData(List<D> list) {
        this(list.size(), list);
    }

    public ListData(Integer total, List<D> list) {
        this.total = total;
        this.list = list;
    }

}
