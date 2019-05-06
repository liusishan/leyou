package com.leyou.page.client;

import com.leyou.item.api.GoodApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: lss
 * @Date: 2019/5/1 17:42
 * @Description:
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodApi {
}
