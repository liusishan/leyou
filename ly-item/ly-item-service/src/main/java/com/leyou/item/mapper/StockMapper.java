package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @Auther: lss
 * @Date: 2019/4/27 22:59
 * @Description:
 */
public interface StockMapper extends BaseMapper<Stock> {

    @Update("UPDATE tb_stock SET stock = stock - #{num} WHERE sku_id = #{id} AND stock >= #{num}")
    int decreaseStock(@Param("id") Long id, @Param("num") Integer num);
}
