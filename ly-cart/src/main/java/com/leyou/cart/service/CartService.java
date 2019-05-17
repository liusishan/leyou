package com.leyou.cart.service;

import com.leyou.auto.pojo.UserInfo;
import com.leyou.cart.interceptors.UserInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: lss
 * @Date: 2019/5/17 15:48
 * @Description:
 */
@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "cart:uid:";

    public void addCart(Cart cart) {
        // 获取 登录用户
        UserInfo user = UserInterceptor.getUser();
        // key
        String key = KEY_PREFIX + user.getId();
        // hashKey
        String hashKey = cart.getSkuId().toString();
        // 记录 num
        Integer cartNum = cart.getNum();
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(key);
        // 判断商品是否存在
        if (ops.hasKey(hashKey)) {
            // 存在，修改数量
            String json = ops.get(hashKey).toString();
            cart = JsonUtils.toBean(json, Cart.class);
            cart.setNum(cart.getNum() + cartNum);
        }
        // 写回 redis
        ops.put(hashKey, JsonUtils.toString(cart));
    }

    public List<Cart> queryCartList() {
        // 获取 登录用户
        UserInfo user = UserInterceptor.getUser();
        // key
        String key = KEY_PREFIX + user.getId();

        if (!redisTemplate.hasKey(key)) {
            // key 不存在，返回 404
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        // 获取登录用户的 所有购物车
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(key);
        List<Cart> carts = ops.values().stream()
                .map(o -> JsonUtils.toBean(o.toString(), Cart.class))
                .collect(Collectors.toList());

        return carts;
    }

    public void updateNum(Long skuId, Integer num) {
        // 获取 登录用户
        UserInfo user = UserInterceptor.getUser();
        // key
        String key = KEY_PREFIX + user.getId();
        // hashKey
        String hashKey = skuId.toString();
        BoundHashOperations<String, Object, Object> ops = redisTemplate.boundHashOps(key);
        // 判断是否存在
        if (!ops.hasKey(hashKey)) {
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        // 查询
        Cart cart = JsonUtils.toBean(ops.get(hashKey).toString(), Cart.class);
        cart.setNum(num);

        // 写回 redis
        ops.put(hashKey, JsonUtils.toString(cart));
    }

    public void deleteCart(Long skuId) {
        // 获取 登录用户
        UserInfo user = UserInterceptor.getUser();
        // key
        String key = KEY_PREFIX + user.getId();
        // 删除
        redisTemplate.opsForHash().delete(key, skuId.toString());
    }
}
