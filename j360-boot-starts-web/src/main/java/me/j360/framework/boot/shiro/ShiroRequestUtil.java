package me.j360.framework.boot.shiro;
import me.j360.framework.common.web.context.NativeAgent;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;


public class ShiroRequestUtil {

    /**
     * 获取请求的客户端及设备信息
     * Client-Agent: ISSUE/3.1.0/1602/iOS/7.1/iPhone 5s (A1457/A1518/A1528/A1530)/7EAB70B1-624F-463A-943C-E7FF235A9A0C/wifi/iOS
     *
     * 在传递参数的时候setOsNumber安卓=1，苹果=iOS
     * wifi 3g 4g
     * channel = ios、android、xiaomi、huawei
     */
    public static NativeAgent getNativeAgent(HttpServletRequest request, String agentHeaderName) {
        String clientAgentString = request.getHeader(agentHeaderName);
        if (StringUtils.isNotEmpty(clientAgentString)) {
            //因为iPhone里面有括号斜线，导致获取失败，用正则去掉（）及里面的内容
            clientAgentString = clientAgentString.replaceAll(" \\(.*?\\)", "");
            String userAgent[] = clientAgentString.split("/");
            if (userAgent.length == 9) {
                NativeAgent clientAgent = new NativeAgent();
                clientAgent.setAppid(userAgent[0]);
                clientAgent.setApp_v(userAgent[1]);
                clientAgent.setBuild(Integer.parseInt(userAgent[2]));
                clientAgent.setOs(Integer.parseInt(userAgent[3]));
                clientAgent.setOs_v(userAgent[4]);
                clientAgent.setC_model(userAgent[5]);
                clientAgent.setCid(userAgent[6]);
                clientAgent.setNet(userAgent[7]);
                clientAgent.setChannel(userAgent[8]);
                return clientAgent;
            }
        }
        return null;
    }

}
