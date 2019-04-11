package me.j360.framework.boot.shiro.filter;

import me.j360.framework.boot.shiro.JwtSignature;
import me.j360.framework.common.web.context.SessionContext;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: min_xu
 * @date: 2019/1/11 4:05 PM
 * 说明：
 */
public class GuestLoginSessionFilter extends PathMatchingFilter {

    private JwtSignature jwtSignature;

    public GuestLoginSessionFilter(JwtSignature jwtSignature) {
        this.jwtSignature = jwtSignature;
    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //设置该请求为无状态请求
        if(((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        request.setAttribute(DefaultSubjectContext.SESSION_CREATION_ENABLED, Boolean.FALSE);
        jwtSignature.createGuest((HttpServletRequest) request);
        return true;
    }


    /**
     *
     */
    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {
        SessionContext.clear();
    }
}