package me.j360.framework.common.web.context;

/**
 * @author: min_xu
 * @date: 2019/1/10 6:34 PM
 * 说明：
 */
public class SessionContext {

    private static final ThreadLocal<DefaultUserSession> UserContextHolder = new ThreadLocal<>();

    private static final ThreadLocal<Long> UidContextHolder = new ThreadLocal<>();




}
