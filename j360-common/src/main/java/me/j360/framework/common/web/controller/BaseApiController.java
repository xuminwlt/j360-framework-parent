package me.j360.framework.common.web.controller;

import me.j360.framework.common.web.context.*;

/**
 * @author: min_xu
 */
public abstract class BaseApiController implements IController {


    public Long getUserId() {
        return SessionContext.getSessionId();
    }

    public DefaultSessionUser getDefaultSession() {
        return (DefaultSessionUser) SessionContext.getBaseSessionUser();
    }

    public NativeAgent getNativeAgent() {
        return getDefaultSession().getAgent();
    }

    public Integer getDevice() {
        return getNativeAgent().getOs();
    }

    public NavigatorAgent getNavigatorAgent() {
        return null;//TODO getWebSessionUser().get();
    }

    public WebSessionUser getWebSessionUser() {
        return (WebSessionUser) SessionContext.getBaseSessionUser();
    }

}
