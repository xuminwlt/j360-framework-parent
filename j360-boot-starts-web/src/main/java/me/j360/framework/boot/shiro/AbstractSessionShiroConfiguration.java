package me.j360.framework.boot.shiro;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import me.j360.framework.boot.shiro.dao.SessionStorageDAO;
import me.j360.framework.boot.shiro.filter.UserLoginSessionFilter;
import me.j360.framework.boot.shiro.realm.SessionRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: min_xu
 * @date: 2018/12/18 5:49 PM
 * 说明：有状态配置
 */

@Slf4j
public abstract class AbstractSessionShiroConfiguration {

    //redirect to error from shiro AuthorizationException
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleException(AuthorizationException e, Model model) {

        // you could return a 404 here instead (this is how github handles 403, so the user does NOT know there is a
        // resource at that location)
        log.info("AuthorizationException was thrown", e);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", HttpStatus.FORBIDDEN.value());
        map.put("message", "No message available");
        model.addAttribute("errors", map);
        return "error";
    }

    @Bean
    public Realm realm() {
        SessionRealm realm = new SessionRealm(sessionStorageDAO());
        realm.setCredentialsMatcher(credentialsMatcher());
        return realm;
    }

    @Bean
    public UserLoginSessionFilter userLoginSessionFilter() {
        UserLoginSessionFilter auth = new UserLoginSessionFilter();
        return auth;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login", "authc"); // need to accept POSTs from the login form
        chainDefinition.addPathDefinition("/logout", "logout");
        chainDefinition.addPathDefinition("/home", "adminAuthc");
        chainDefinition.addPathDefinition("/unauthenticated", "anon");
        return chainDefinition;
    }

    @ModelAttribute(name = "subject")
    public Subject subject() {
        return SecurityUtils.getSubject();
    }


    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher =  new HashedCredentialsMatcher("SHA-256");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    @Autowired
    protected SecurityManager securityManager;

    @Autowired
    protected ShiroFilterChainDefinition shiroFilterChainDefinition;


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired UserLoginSessionFilter userLoginSessionFilter) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/home");
        filterFactoryBean.setUnauthorizedUrl("/unauthenticated");

        HashMap<String, Filter> filters = Maps.newHashMap();
        filters.put("adminAuthc", userLoginSessionFilter);
        filterFactoryBean.setFilters(filters);
        filterFactoryBean.setSecurityManager(securityManager);
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition.getFilterChainMap());

        return filterFactoryBean;
    }


    @Bean
    public DefaultWebSecurityManager securityManager(@Autowired Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    public abstract SessionStorageDAO sessionStorageDAO();
}
