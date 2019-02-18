package me.j360.framework.common.web.context;

import lombok.Data;

/**
 * @author: min_xu
 * @date: 2019/1/11 10:43 PM
 * 说明：
 */
@Data
public class WebSessionUser extends BaseSessionUser {

    private NavigatorAgent agent;

    @Override
    protected void setAgent(BaseAgent agent) {
        this.agent = (NavigatorAgent) agent;
    }

    @Override
    protected BaseAgent getAgent() {
        return agent;
    }
}
