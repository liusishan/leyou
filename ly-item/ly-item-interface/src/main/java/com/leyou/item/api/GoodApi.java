package com.leyou.item.api;

import com.leyou.common.dto.CartDto;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: lss
 * @Date: 2019/5/1 17:50
 * @Description:
 */
public interface GoodApi {
    /**
     * 根据 spu 的 id 查询详情 detail
     *
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{id}")
    SpuDetail queryDetailById(@PathVariable("id") Long spuId);

    /**
     * 根据 spu 查询下面的所有 sku
     *
     * @param spuId
     * @return
     */
    @GetMapping("sku/list")
    List<Sku> querySkuBySpuId(@RequestParam("id") Long spuId);

    /**
     * 分页查询 spu
     *
     * @param page
     * @param rows
     * @param saleable
     * @param search
     * @return
     */
    @GetMapping("/spu/page")
    PageResult<Spu> querySpuByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                   @RequestParam(value = "saleable", required = false) Boolean saleable,
                                   @RequestParam(value = "key", required = false) String search
    );

    /**
     * 根据 spu 的 id 查询 spu
     *
     * @param id
     * @return
     */
    @GetMapping("spu/{id}")
    Spu querySpuById(@PathVariable("id") Long id);

    /**
     * 根据 sku 的 id 集合查询所有 sku
     *
     * @param ids
     * @return
     */
    @GetMapping("sku/list/ids")
    List<Sku> querySkuByIds(@RequestParam("ids") List<Long> ids);

    /**
     * 减库存
     * @param carts
     */
    @PostMapping("stock/decrease")
    void decreaseStock(@RequestBody List<CartDto> carts);
}
