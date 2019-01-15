package me.j360.framework.boot.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Objects;

/**
 * @author: min_xu
 * @date: 2019/1/11 10:39 AM
 * 说明：有状态会话Context
 */
public class SessionContextFilter extends PathMatchingFilter {

    protected Subject getSubject(ServletRequest request, ServletResponse response) {
        return SecurityUtils.getSubject();
    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        if (Objects.nonNull(subject.getPrincipal())) {
            //set context

//            if (user != null) {
//                MDC.put("userId", user.getId().toString());
//                MDC.put("userName", user.getUsername());
//            } else {
//                MDC.put("userId", "");
//                MDC.put("userName", "");
//            }
        }
        return true;
    }


    /**
     *
     */
    @Override
    public void afterCompletion(ServletRequest request, ServletResponse response, Exception exception) throws Exception {

    }

}
