package me.j360.framework.core.kit.id;

import com.vip.vjtools.vjkit.id.IdUtil;

public class Identities {

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
     */
    public static String uuid() {
        return IdUtil.fastUUID().toString();
    }

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid2() {
        return IdUtil.fastUUID().toString().replaceAll("-", "");
    }

}
