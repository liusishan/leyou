package com.leyou.page.web;

import com.leyou.page.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @Auther: lss
 * @Date: 2019/5/4 20:33
 * @Description:
 */
@Controller
public class PageController {

    private final PageService pageService;

    @Autowired
    public PageController(PageService pageService) {
        this.pageService = pageService;
    }


    @GetMapping("item/{id}.html")
    public String toItemPage(@PathVariable("id") Long spuId, Model model){
        // 查询视图模型
        Map<String,Object> attributes = pageService.loadModel(spuId);

        // 准备模型数据
        model.addAllAttributes(attributes);
        return "item";
    }
}
