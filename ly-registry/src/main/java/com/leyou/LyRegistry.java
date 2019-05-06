package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Auther: lss
 * @Date: 2019/4/11 11:12
 * @Description:
 */
@EnableEurekaServer
@SpringBootApplication
public class LyRegistry {

    public static void main(String[] args) {
        SpringApplication.run(LyRegistry.class);
    }
}
