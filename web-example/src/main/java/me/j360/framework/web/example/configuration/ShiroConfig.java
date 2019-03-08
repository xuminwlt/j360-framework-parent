package me.j360.framework.web.example.configuration;

import com.auth0.jwt.algorithms.Algorithm;
import com.google.common.collect.Maps;
import me.j360.framework.boot.shiro.JwtSignature;
import me.j360.framework.boot.shiro.dao.SessionStorageDAO;
import me.j360.framework.boot.shiro.filter.AgentContextFilter;
import me.j360.framework.boot.shiro.filter.TokenAuthcFilter;
import me.j360.framework.boot.shiro.filter.TokenContextFilter;
import me.j360.framework.boot.shiro.realm.TokenRealm;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: min_xu
 * @date: 2019/1/11 11:26 AM
 * 说明：
 */

@Configuration
public class ShiroConfig {

    @Autowired
    private RedissonClient redissonClient;

    @Value("${shiro.issue}")
    private String issue;
    @Value("${shiro.secret}")
    private String secret;

    @Bean
    public SessionStorageDAO sessionStorageDAO() {
        RedissonSessionStorageDAO sessionStorageDAO = new RedissonSessionStorageDAO();
        sessionStorageDAO.setRedissonClient(redissonClient);
        return sessionStorageDAO;
    }

    @Bean
    public Realm realm() {
        TokenRealm realm = new TokenRealm(sessionStorageDAO());
        realm.setCredentialsMatcher(new SimpleCredentialsMatcher());
        return realm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        Map pathFilterMap = this.getFilterPathFilterMap();
        chainDefinition.addPathDefinitions(pathFilterMap);
        return chainDefinition;
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired SecurityManager securityManager, @Autowired ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setLoginUrl("/login");
        filterFactoryBean.setSuccessUrl("/success");
        filterFactoryBean.setUnauthorizedUrl("/unauthenticated");
        filterFactoryBean.setFilters(getCustomFilters());
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


    public TokenContextFilter tokenContextFilter() {
        TokenContextFilter context = new TokenContextFilter(this.jwtSignature());
        return context;
    }

    public TokenAuthcFilter tokenAuthcFilter() {
        TokenAuthcFilter authc = new TokenAuthcFilter(this.jwtSignature(), this.sessionStorageDAO());
        return authc;
    }

    public AgentContextFilter agentAuthcFilter() {
        AgentContextFilter filter = new AgentContextFilter("Client-Agent");
        return filter;
    }

    @Bean
    public Algorithm algorithm() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return algorithm;
    }

    @Bean
    public JwtSignature jwtSignature() {
        JwtSignature jwtSignature = new JwtSignature(algorithm(), sessionStorageDAO());
        jwtSignature.setJWT_ISSUER(issue);
        return jwtSignature;
    }



    public Map<String, String> getFilterPathFilterMap() {
        Map<String, String> filters = Maps.newLinkedHashMap();
        filters.put("/", "anon");
        filters.put("/swagger-ui.html", "anon");
        filters.put("/swagger-resources/**", "anon");
        filters.put("/csrf", "anon");
        filters.put("/error", "anon");
        filters.put("/v2/**", "anon");
        filters.put("/webjars/**", "anon");
        filters.put("/ws/**", "anon");
        filters.put("/api/user/guest", "anon");
        filters.put("/api/user/test", "context");
        filters.put("/api/**", "context, tokenAuthc, agent");
        return filters;
    }

    public Map<String, Filter> getCustomFilters() {
        HashMap<String, Filter> filters = Maps.newLinkedHashMap();
        filters.put("context", tokenContextFilter());
        filters.put("tokenAuthc", tokenAuthcFilter());
        filters.put("agent", agentAuthcFilter());
        return filters;
    }


}
