package me.j360.framework.common.web.context;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author: min_xu
 * @date: 2019/1/10 6:33 PM
 * 说明：
 */

@Data
public class DefaultSessionUser extends BaseSessionUser {

    @JsonIgnore
    protected NativeAgent agent;

    @Override
    public void setAgent(BaseAgent agent) {
        this.agent = (NativeAgent) agent;
    }
}
