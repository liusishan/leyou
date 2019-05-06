package com.leyou.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Auther: lss
 * @Date: 2019/4/11 11:19
 * @Description:
 */
@EnableZuulProxy
@SpringCloudApplication
public class LyGateway {

    public static void main(String[] args) {
        SpringApplication.run(LyGateway.class);
    }
}
