package me.j360.framework.common.web.context;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * @author: min_xu
 * @date: 2019/1/10 6:33 PM
 * 说明：
 */

@Builder
@Data
public class DefaultJwtSessionUser extends DefaultSessionUser {

    private String jwt;
    private Set<String> roles;

}
