package me.j360.framework.boot.shiro.realm;

import me.j360.framework.boot.shiro.dao.SessionStorageDAO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.apache.shiro.web.subject.WebSubject;

import javax.servlet.ServletRequest;
import java.util.Set;

/**
 * @author: min_xu
 * @date: 2019/1/10 11:09 PM
 * 说明：
 */
public class SessionRealm extends AuthorizingRealm {

    private ByteSource commonSalt;
    private SessionStorageDAO sessionStorageDAO;

    public void setCommonSalt(String commonSalt) {
        this.commonSalt = new SimpleByteSource(commonSalt);
    }

    public SessionRealm(SessionStorageDAO sessionStorageDAO) {
        this.sessionStorageDAO = sessionStorageDAO;
    }

    /**
     * 对Token的支持类型
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        return  (token instanceof AuthenticationToken);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Long id = (Long) principals.getPrimaryPrincipal();
        Set<String> roles = sessionStorageDAO.roles(id);
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(roles);


        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token){

        //用户名和密码的登录过程
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        ServletRequest request = ((WebSubject) SecurityUtils.getSubject()).getServletRequest();

        //todo from db
        String hashedPass = "";//encodedPassword("password", commonSalt);
        //增加邮件验证码验证登录，需要前端配合，暂时关闭
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                username, //用户名
                hashedPass, //密码 处理后的 admin
                commonSalt,//salt=salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}