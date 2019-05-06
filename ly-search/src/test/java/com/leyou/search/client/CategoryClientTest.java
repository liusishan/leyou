package com.leyou.search.client;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.Spu;
import com.leyou.search.pojo.Goods;
import com.leyou.search.repository.GoodsRepository;
import com.leyou.search.service.SearchService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @Auther: lss
 * @Date: 2019/5/1 17:37
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryClientTest {

    @Autowired
    private CategoryClient client;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GoodsRepository repository;

    @Test
    public void queryCategoryByIds() {
        List<Category> list = client.queryCategoryByIds(Arrays.asList(1L, 2L, 3L));
        Assert.assertEquals(3, list.size());
        for (Category category : list) {
            System.out.println(category);
        }
    }

    @Test
    public void LoadData() {
        // 查询spu信息
        int page = 1;
        int rows = 100;
        int size = 0;
        do {
            PageResult<Spu> result = goodsClient.querySpuByPage(page, rows, true, null);
            List<Spu> spuList = result.getItems();
            if (CollectionUtils.isEmpty(spuList))
                break;

            // 构建成 goods
            List<Goods> goodsList = spuList.stream()
                    .map(searchService::buildGoods).collect(Collectors.toList());

            // 存入索引库
            repository.saveAll(goodsList);
            // 翻页
            page++;
            size = spuList.size();
            //
        } while (size == 100);
    }
}