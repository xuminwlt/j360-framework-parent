package me.j360.framework.boot.shiro.filter;

import me.j360.framework.boot.shiro.JwtSignature;
import me.j360.framework.common.web.context.SessionContext;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author: min_xu
 * @date: 2019/1/11 4:05 PM
 * 说明：用于无状态登录
 */
public class UserLoginTokenFilter extends PathMatchingFilter {

    private JwtSignature jwtSignature;

    public UserLoginTokenFilter(JwtSignature jwtSignature) {
        this.jwtSignature = jwtSignature;
    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //设置该请求为无状态请求
        request.setAttribute(DefaultSubjectContext.SESSION_CREATION_ENABLED, Boolean.FALSE);
        String jwt = jwtSignature.createUser();
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