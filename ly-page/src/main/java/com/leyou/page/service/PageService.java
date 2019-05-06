package com.leyou.page.service;

import com.leyou.item.pojo.*;
import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecificationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lss
 * @Date: 2019/5/5 10:58
 * @Description:
 */
@Slf4j
@Service
public class PageService {

    private final BrandClient brandClient;

    private final CategoryClient categoryClient;

    private final SpecificationClient specClient;

    private final GoodsClient goodsClient;

    private final TemplateEngine templateEngine;

    @Autowired
    public PageService(BrandClient brandClient, CategoryClient categoryClient, SpecificationClient specClient, GoodsClient goodsClient, TemplateEngine templateEngine) {
        this.brandClient = brandClient;
        this.categoryClient = categoryClient;
        this.specClient = specClient;
        this.goodsClient = goodsClient;
        this.templateEngine = templateEngine;
    }

    public Map<String, Object> loadModel(Long spuId) {
        Map<String, Object> model = new HashMap<>();
        // 查询 spu
        Spu spu = goodsClient.querySpuById(spuId);
        // 查询 skus
        List<Sku> skus = spu.getSkus();
        // 查询详情
        SpuDetail detail = spu.getSpuDetail();

        // 装填模型数据
        model.put("spu", spu);
        model.put("skus", skus);
        model.put("spuDetail", detail);


        // 查询商品分类
        List<Category> categories = categoryClient.queryCategoryByIds(
                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        model.put("categories", categories);

        // 查询 brand
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        model.put("brand", brand);

        // 查询规格组及组内参数
        List<SpecGroup> groups = specClient.queryGroupByCid(spu.getCid3());
        model.put("groups", groups);

        // 查询商品分类下的特有规格参数
        List<SpecParam> params = specClient.queryParamList(null, spu.getCid3(), null, false);
        // 处理成id:name格式的键值对
        Map<Long, String> paramMap = new HashMap<>();
        for (SpecParam param : params) {
            paramMap.put(param.getId(), param.getName());
        }

        model.put("paramMap", paramMap);

        return model;
    }

    public void createHtml(Long spuId) {
        // 上下文
        Context context = new Context();
        context.setVariables(loadModel(spuId));
        // 输出流
        File dest = new File("D:\\upload", spuId + ".html");

        if (dest.exists()) {
            boolean b = dest.delete();
        }

        try (PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
            // 生成HTML
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            log.error("[静态页服务] 生成静态页异常！", e);
        }

    }

    public void deleteHtml(Long spuId) {
        File dest = new File("D:\\upload", spuId + ".html");
        if (dest.exists()) {
            dest.delete();
        }
    }
}
