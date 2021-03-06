package me.j360.framework.boot.shiro.filter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 用于有状态浏览器会话登录
 * unauthenticated接口根据是否ajax自定义responseBody
 */

@Slf4j
public class UserLoginSessionFilter extends AccessControlFilter {

    public static final String DEFAULT_UNAUTHENTICATED_URL = "/unauthenticated";

    public static final String DEFAULT_ERROR_KEY_ATTRIBUTE_NAME = "shiroAuthzFailure";

    @Getter
    @Setter
    private String unauthenticatedUrl = DEFAULT_UNAUTHENTICATED_URL;

    @Getter
    @Setter
    private String failureKeyAttribute = DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if(((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            // 未认证
            Subject subject = getSubject(request, response);
            return subject.isAuthenticated();
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        request.setAttribute(getFailureKeyAttribute(), new UnauthenticatedException("没有登录,不能访问"));
        try {
            //set error info
            request.getRequestDispatcher(getUnauthenticatedUrl()).forward(request, response);
        } catch (Exception e) {
            // 如果无法跳转,则直接返回401
            if (log.isWarnEnabled()) {
                log.warn("无法Forward到UnauthorizedUrl,直接返回401", e);
            }
            WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return false;
    }
}
