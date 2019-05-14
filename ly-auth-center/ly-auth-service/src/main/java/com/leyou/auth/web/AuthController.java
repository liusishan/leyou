package com.leyou.auth.web;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.auto.pojo.UserInfo;
import com.leyou.auto.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.CookieUtils;
import com.leyou.user.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: lss
 * @Date: 2019/5/14 19:49
 * @Description:
 */
@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Value("${ly.jwt.cookieName}")
    private String cookieName;

    @Autowired
    private JwtProperties prop;

    /**
     * 登录授权
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<Void> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletResponse response,
            HttpServletRequest request) {
        // 登录
        String token = authService.login(username, password);
        // 写入 cookie
        CookieUtils.newBuilder(response).httpOnly().request(request)
                .build(cookieName, token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 验证用户信息
     *
     * @param token
     * @param request
     * @param response
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(
            @CookieValue("LY_TOKEN") String token,
            HttpServletRequest request, HttpServletResponse response
    ) {
        try {
            // 解析 token
            UserInfo info = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            // 刷新 token,重新生成 token
            String newToken = JwtUtils.generateToken(info, prop.getPrivateKey(), prop.getExpire());
            // 写入cookie
            CookieUtils.newBuilder(response).httpOnly().request(request)
                    .build(cookieName, newToken);
            // 已登录，返回用户信息
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            // token 已过期，或者 token 无效
            throw new LyException(ExceptionEnum.UNAUTHORIZED);
        }
    }
}
