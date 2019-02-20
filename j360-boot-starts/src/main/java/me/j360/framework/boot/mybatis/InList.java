package me.j360.framework.boot.mybatis;

import org.springframework.util.StringUtils;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;

public class InList<T> extends AbstractList<T> {

    private List<T> target;

    public InList(List<T> target) {
        this.target = target;
    }

    @Override
    public T get(int index) {
        return target.get(index);
    }

    @Override
    public Iterator<T> iterator() {
        return target.iterator();
    }

    @Override
    public int size() {
        return target.size();
    }

    @Override
    public String toString() {
        return "(" + StringUtils.collectionToDelimitedString(target, ",", "'", "'") + ")";
    }
}
