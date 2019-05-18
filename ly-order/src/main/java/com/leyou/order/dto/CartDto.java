package com.leyou.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: lss
 * @Date: 2019/5/18 12:58
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Long skuId; // 商品skuId
    private Integer num; // 购买数量
}
