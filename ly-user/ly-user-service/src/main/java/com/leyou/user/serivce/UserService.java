package com.leyou.user.serivce;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.user.utils.CodecUtils;
import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: lss
 * @Date: 2019/5/13 20:06
 * @Description:
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "user:verify:phone:";

    public Boolean checkData(String data, Integer type) {
        User record = new User();
        // 判断数据类型
        switch (type) {
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                throw new LyException(ExceptionEnum.INVALID_USER_DATA_TYPE);
        }
        return userMapper.selectCount(record) == 0;
    }

    public void sendCode(String phone) {
        // 生成 key
        String key = KEY_PREFIX + phone;
        // 生成验证码
        String code = NumberUtils.generateCode(6);

        Map<String, String> msg = new HashMap<>();
        msg.put("phone", phone);
        msg.put("code", code);

        // 发送验证码
        amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", msg);
        // 保存验证码
        redisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
    }

    public User queryUser(String username, String password) {
        User record = new User();
        record.setUsername(username);
        // 查询用户
        User user = userMapper.selectOne(record);
        // 校验用户名
        if (user == null)
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        // 校验密码
        if (!StringUtils.equals(user.getPassword(), CodecUtils.md5Hex(password, user.getSalt())))
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        // 用户名和密码正确
        return user;
    }

    @Transactional
    public void register(User user, String code) {
        String key = KEY_PREFIX + user.getPhone();
        // 校验验证码
        String cacheCode = redisTemplate.opsForValue().get(key);
        if (!StringUtils.equals(code, cacheCode))
            throw new LyException(ExceptionEnum.INVALID_VERIFY_CODE);

        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);

        // 对密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        user.setCreated(new Date());
        // 写入数据库
        boolean b = userMapper.insert(user) == 1;

        // 如果注册成功，删除redis中的code
        if (b) {
            try {
                redisTemplate.delete(key);
            } catch (Exception e) {
                log.error("删除缓存验证码失败，code：{}", code, e);
            }
        }
    }
}
