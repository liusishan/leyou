package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Auther: lss
 * @Date: 2019/5/1 18:04
 * @Description:
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
