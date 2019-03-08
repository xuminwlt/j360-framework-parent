package me.j360.framework.web.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 说明：服务端启动入口
 */
@ComponentScan({"me.j360.framework.web.example"})
@SpringBootApplication
@EnableSwagger2
public class BootstrapApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(BootstrapApplication.class, args);
    }
}
