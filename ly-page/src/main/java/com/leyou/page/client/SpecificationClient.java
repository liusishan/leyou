package com.leyou.page.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Auther: lss
 * @Date: 2019/5/1 18:00
 * @Description:
 */
@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
