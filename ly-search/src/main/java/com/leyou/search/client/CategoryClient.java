package com.leyou.search.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: lss
 * @Date: 2019/5/1 17:34
 * @Description:
 */
@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
}
