package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: lss
 * @Date: 2019/5/14 20:30
 * @Description:
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
