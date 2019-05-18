package com.leyou.order.client;

import com.leyou.item.api.GoodApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: lss
 * @Date: 2019/5/18 16:22
 * @Description:
 */
@FeignClient("item-service")
public interface GoodsClient extends GoodApi {
}
