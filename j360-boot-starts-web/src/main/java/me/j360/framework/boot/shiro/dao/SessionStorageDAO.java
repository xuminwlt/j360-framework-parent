package me.j360.framework.boot.shiro.dao;

import me.j360.framework.common.web.context.BaseSessionUser;
import me.j360.framework.common.web.context.DefaultSessionUser;

import java.util.Set;

/**
 * @author: min_xu
 * @date: 2019/1/11 4:30 PM
 * 说明：
 */
public interface SessionStorageDAO {

    void save(BaseSessionUser sessionUser);

    DefaultSessionUser get(String sessionId);

    Set<String> roles(Long principal);
}
