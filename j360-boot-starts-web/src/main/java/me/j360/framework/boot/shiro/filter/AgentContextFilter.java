package me.j360.framework.boot.shiro.filter;

import me.j360.framework.boot.shiro.ShiroRequestUtil;
import me.j360.framework.common.web.context.NativeAgent;
import me.j360.framework.common.web.context.SessionContext;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author: min_xu
 * @date: 2019/1/11 10:38 AM
 * 说明：
 */
public class AgentContextFilter extends PathMatchingFilter {

    private String agentName;

    public AgentContextFilter(String agentName) {
        this.agentName = agentName;
    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //设置该请求为无状态请求
        if(((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        NativeAgent agent = ShiroRequestUtil.getNativeAgent((HttpServletRequest) request, agentName);
        if (Objects.nonNull(agent)) {
            if (Objects.nonNull(SessionContext.getBaseSessionUser())) {
                SessionContext.getBaseSessionUser().setAgent(agent);
            }
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
