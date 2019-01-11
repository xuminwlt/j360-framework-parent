package me.j360.framework.common.web.context;

import lombok.Data;

/**
 * @author: min_xu
 * @date: 2019/1/10 6:31 PM
 * 说明：Native App Client Agent
 */

@Data
public class NativeAgent extends BaseAgent {

    private String appid;
    private Integer os;
    private Integer build;
    private String os_v;
    private String app_v;
    private String net;
    private String channel;

}
