package me.j360.framework.common.web.context;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: min_xu
 * @date: 2019/1/10 6:32 PM
 * 说明：
 */
public abstract class BaseSessionUser implements SessionUser {

    public abstract void setAgent(BaseAgent agent);
    public abstract BaseAgent getAgent();

    @Getter
    @Setter
    protected Long uid;
    @Getter
    @Setter
    protected String cid;
    @Getter
    @Setter
    protected String sessionId;
}
