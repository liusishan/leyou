package com.leyou.auth;

import com.leyou.auto.pojo.UserInfo;
import com.leyou.auto.utils.JwtUtils;
import com.leyou.auto.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Auther: lss
 * @Date: 2019/5/14 15:39
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JwtTest.class)
public class JwtTest {


    private static final String pubKeyPath = "D:\\leyou\\rsa\\rsa.pub";

    private static final String priKeyPath = "D:\\leyou\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "123");
    }


    @Before
    public void testGetRsa() throws Exception {
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成 token
        String token = JwtUtils.generateToken(new UserInfo(123L, "jack"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MTIzLCJ1c2VybmFtZSI6ImphY2siLCJleHAiOjE1NTc4MjcwMjV9.JJkQGGr5tlCsx12wPbhflTw1OO7R5b5uPSr14hLc1k0GvsRaVPYK9LOYBQyIVrcm7H3l-f6913kCZCo7_tMqIwl-UF5zgssEPJCBY1b35GuCIyESEpF9L7vh-LF9CYBYFAjP4ymyaQ-l_vFX2rcIHi1SzyChvumkaxt4N__F8ZE";

        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id = "+user.getId());
        System.out.println("username = "+user.getUsername());
    }
}
