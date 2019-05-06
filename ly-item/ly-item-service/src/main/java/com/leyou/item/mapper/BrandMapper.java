package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Auther: lss
 * @Date: 2019/4/22 19:35
 * @Description:
 */
public interface BrandMapper extends BaseMapper<Brand> {
    /**
     * 新增商品分类和品牌中间表数据
     *
     * @param cid
     * @param bid
     * @return
     */
    @Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
    int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

    @Select("SELECT b.* FROM tb_category_brand cb LEFT JOIN tb_brand b ON cb.brand_id = b.id WHERE cb.category_id = #{cid}")
    List<Brand> queryByCategoryId(@Param("cid") Long cid);
}
