package com.leyou.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: lss
 * @Date: 2019/4/25 16:28
 * @Description:
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specService;

    /**
     * 根据分类 id 查询规格组
     *
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid) {
        return ResponseEntity.ok(specService.queryGroupByCid(cid));
    }

    /**
     * 新增商品规格组
     *
     * @param specGroup
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Void> saveGroup(@RequestBody SpecGroup specGroup) {
        specService.saveGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改商品规格组
     *
     * @param specGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Void> updateGroup(@RequestBody SpecGroup specGroup) {
        specService.saveGroup(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据 id 删除商品规格组
     *
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id) {
        specService.deleteGroup(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 查询参数集合
     *
     * @param gid       组 id
     * @param cid       分类 id
     * @param searching 是否搜索
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamList(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching,
            @RequestParam(value = "generic", required = false) Boolean generic) {

        return ResponseEntity.ok(specService.queryParamList(gid, cid, searching,generic));
    }

    /**
     * 新增商品规格参数
     *
     * @param specParam
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Void> saveParam(@RequestBody SpecParam specParam) {
        specService.saveParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改商品规格参数
     *
     * @param specParam
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Void> updateParam(@RequestBody SpecParam specParam) {
        specService.updateParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据 id 删除商品规格参数
     *
     * @param id
     * @return
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteParam(@PathVariable Long id) {
        specService.deleteParam(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据分类 id 查询规格组及组内参数
     *
     * @param cid
     * @return
     */
    @GetMapping("group")
    public ResponseEntity<List<SpecGroup>> queryListByCid(@RequestParam("cid") Long cid) {
        return ResponseEntity.ok(specService.queryListByCid(cid));
    }

}
