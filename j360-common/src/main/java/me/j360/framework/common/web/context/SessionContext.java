package me.j360.framework.common.web.context;

import java.util.Objects;

/**
 * @author: min_xu
 * @date: 2019/1/10 6:34 PM
 * 说明：
 */
public class SessionContext {

    private static final ThreadLocal<BaseSessionUser> UserContextHolder = new ThreadLocal<>();

    private static final ThreadLocal<Long> UidContextHolder = new ThreadLocal<>();

    public static void setSessionUser(BaseSessionUser sessionUser) {
        if (Objects.nonNull(sessionUser)) {
            UserContextHolder.set(sessionUser);
            UidContextHolder.set(sessionUser.getUid());
        }
    }

    public static BaseSessionUser getBaseSessionUser() {
        return UserContextHolder.get();
    }

    public static Long getSessionId() {
        return UidContextHolder.get();
    }

    public static void clear() {
        UserContextHolder.remove();
        UidContextHolder.remove();
    }

}
