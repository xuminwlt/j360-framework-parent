package me.j360.framework.common.web.context;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: min_xu
 * @date: 2019/1/10 6:32 PM
 * 说明：
 */
public abstract class BaseUserSession {

    @Getter
    @Setter
    private BaseAgent agent;

}
