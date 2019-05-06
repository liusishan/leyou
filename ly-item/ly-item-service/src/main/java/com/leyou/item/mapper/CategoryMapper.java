package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Auther: lss
 * @Date: 2019/4/22 10:10
 * @Description:
 */
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category, Long> {

}
