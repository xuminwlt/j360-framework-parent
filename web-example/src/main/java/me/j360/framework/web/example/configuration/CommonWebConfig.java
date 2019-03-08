package me.j360.framework.web.example.configuration;

import lombok.extern.slf4j.Slf4j;
import me.j360.framework.boot.message.MessageSourceBundler;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author: min_xu
 * @date: 2019/1/11 5:53 PM
 * 说明：
 */

@Slf4j
@Configuration
public class CommonWebConfig {

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private String redisPort;

    @Bean(name = "redissonClient", destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + redisHost + ":" + redisPort)
                .setDatabase(0)
                .setConnectionPoolSize(3)
                .setConnectionMinimumIdleSize(2)
                .setSubscriptionConnectionMinimumIdleSize(2)
                .setSubscriptionConnectionPoolSize(3)
                .setConnectTimeout(5000)
                .setTimeout(3000);
        return Redisson.create(config);
    }

    @Bean
    public ResourceBundleMessageSource resourceBundleMessageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames("messages.result", "messages.validation");
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        resourceBundleMessageSource.setDefaultEncoding("utf-8");
        return resourceBundleMessageSource;
    }

    @Bean
    public MessageSourceBundler messageSourceBundler(@Autowired ResourceBundleMessageSource resourceBundleMessageSource) {
        MessageSourceBundler messageSourceBundler = new MessageSourceBundler();
        messageSourceBundler.setMessageSource(resourceBundleMessageSource);
        return messageSourceBundler;
    }
}
