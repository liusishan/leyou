package com.leyou.gateway.fifters;

import com.leyou.auto.pojo.UserInfo;
import com.leyou.auto.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.gateway.config.FilterProperites;
import com.leyou.gateway.config.JwtProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: lss
 * @Date: 2019/5/14 23:28
 * @Description:
 */
@Component
@EnableConfigurationProperties({JwtProperties.class, FilterProperites.class})
public class AuthFilter extends ZuulFilter {

    @Autowired
    private JwtProperties prop;

    @Autowired
    private FilterProperites filterProp;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; // 过滤器类型，前置过滤
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1; // 过滤器顺序
    }

    // 是否过滤
    @Override
    public boolean shouldFilter() {
        // 获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取 request
        HttpServletRequest request = ctx.getRequest();
        // 获取请求的 url 路径
        String path = request.getRequestURI();

        // 判断是否放行，放行 返回 false
        return !isAllowPath(path);
    }

    private boolean isAllowPath(String path) {
        List<String> paths = filterProp.getAllowPaths();
        // 遍历白名单
        for (String allowPath : paths) {
            // 判断是否允许
            if (path.startsWith(allowPath))
                return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        // 获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        // 获取 request
        HttpServletRequest request = ctx.getRequest();
        // 获取 token
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        try {
            // 解析 token
            UserInfo info = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            // TODO 校验权限

        } catch (Exception e) {
            // 解析 token 失败，未登录，拦截
            ctx.setSendZuulResponse(false);
            // 返回状态码
            ctx.setResponseStatusCode(403);
        }
        //
        return null;
    }
}
