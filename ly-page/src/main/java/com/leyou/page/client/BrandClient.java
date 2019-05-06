package com.leyou.page.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: lss
 * @Date: 2019/5/1 17:57
 * @Description:
 */
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
