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
    public void setAgent(BaseAgent agent) {
        this.agent = (NavigatorAgent) agent;
    }

    @Override
    public BaseAgent getAgent() {
        return agent;
    }
}
