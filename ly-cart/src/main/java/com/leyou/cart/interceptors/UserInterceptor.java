package com.leyou.cart.interceptors;

import com.leyou.auto.pojo.UserInfo;
import com.leyou.auto.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: lss
 * @Date: 2019/5/17 15:14
 * @Description:
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    private JwtProperties prop;
    // 使用 ThreadLocal 来存储 user
    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    public UserInterceptor(JwtProperties prop) {
        this.prop = prop;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取 cookie 中的 token
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        // 解析 token
        try {
            UserInfo user = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            // 传递 user
            tl.set(user);
            // 放行
            return true;
        } catch (Exception e) {
            log.error("[购车服务] 解析用户身份失败.", e);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 最后用完数据，一定要清空
        tl.remove();
    }

    public static UserInfo getUser() {
        return tl.get();
    }
}
