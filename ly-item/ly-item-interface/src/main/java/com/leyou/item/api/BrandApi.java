package com.leyou.item.api;

import com.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Auther: lss
 * @Date: 2019/5/1 17:56
 * @Description:
 */
public interface BrandApi {

    @GetMapping("brand/{id}")
    Brand queryBrandById(@PathVariable("id") Long id);

    @GetMapping("brand/brands")
    List<Brand> queryBrandByIds(@RequestParam("ids") List<Long> ids);
}
