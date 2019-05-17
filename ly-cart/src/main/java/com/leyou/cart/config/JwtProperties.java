package com.leyou.cart.config;

import com.leyou.auto.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @Auther: lss
 * @Date: 2019/5/14 17:44
 * @Description:
 */
@Data
@ConfigurationProperties(prefix = "ly.jwt")
public class JwtProperties {

    private String pubKeyPath;
    private String cookieName;

    private PublicKey publicKey; // 公钥


    // 类一但实例化后，就应该读取公钥
    // 该注解在构造函数执行之后执行
    @PostConstruct
    public void init() throws Exception {
        // 读取公钥
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);

    }
}
