package me.j360.framework.boot.controller;

import me.j360.framework.common.web.controller.BaseApiController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: min_xu
 */
public abstract class BaseController extends BaseApiController {


    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 获取客户端IP
     */
    public String getRemoteRequestIP() {
        String remoteIp = httpServletRequest.getHeader("X-Real-IP");
        if (StringUtils.isBlank(remoteIp)) {
            String xff = httpServletRequest.getHeader("X-Forwarded-For");
            if (!StringUtils.isBlank(xff)) {
                remoteIp = xff.split(",")[0];
            }
        }
        if (StringUtils.isEmpty(remoteIp)) {
            remoteIp = httpServletRequest.getRemoteAddr();
        }
        return remoteIp;
    }
}
