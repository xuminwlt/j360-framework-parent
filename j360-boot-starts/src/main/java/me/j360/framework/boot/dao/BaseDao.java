package me.j360.framework.boot.dao;


import me.j360.framework.base.domain.model.BaseModel;
import me.j360.framework.base.domain.rpc.query.BaseQuery;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<M extends BaseModel<ID>, ID extends Serializable, Q extends BaseQuery> {

    /**
     * 增加
     *
     * @param m
     */
    void add(M m);

    /**
     * 删除
     *
     * @param m
     */
    void delete(M m);

    /**
     * 更新
     *
     * @param m
     */
    void update(M m);

    /**
     * 获取
     *
     * @param id 唯一标识
     * @return
     */
    M get(ID id);

    /**
     * 查询
     *
     * @param ids
     * @return
     */
    List<M> findIn(List<ID> ids);

    /**
     * 查询
     *
     * @param query 查询参数
     * @return
     */
    List<M> query(Q query);


    /**
     * 获取Long型统计数据
     * @param table
     * @param column
     * @param id
     * @return
     */
    default Long getCount(String table, String column, Long id) {
        return 0L;
    }

    /**
     * 更新Long型统计数据一次,add=true:+1,add=false:-1
     * @param add
     * @param table
     * @param column
     * @param id
     */
    default void updateCountOne(boolean add, String table, String column, Long id) {

    }

    /**
     * 更新并替换Long数据
     * @param table
     * @param column
     * @param id
     * @param value
     */
    default void updateCountValue(String table, String column, Long id, Long value) {

    }

}
