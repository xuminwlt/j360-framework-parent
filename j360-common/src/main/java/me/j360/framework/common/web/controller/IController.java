package me.j360.framework.common.web.controller;

import me.j360.framework.common.web.context.DefaultSessionUser;
import me.j360.framework.common.web.context.NativeAgent;
import me.j360.framework.common.web.context.NavigatorAgent;
import me.j360.framework.common.web.context.WebSessionUser;

/**
 * @author: min_xu
 */
public interface IController {

    Long getUserId();

    DefaultSessionUser getDefaultSession();

    NativeAgent getNativeAgent();

    Integer getDevice();

    NavigatorAgent getNavigatorAgent();

    WebSessionUser getWebSessionUser();
}
