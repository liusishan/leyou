package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lss
 * @Date: 2019/4/25 16:27
 * @Description:
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;


    public List<SpecGroup> queryGroupByCid(Long cid) {
        // 查询条件
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        // 查询
        List<SpecGroup> list = groupMapper.select(group);
        if (CollectionUtils.isEmpty(list))
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        return list;
    }

    public void saveGroup(SpecGroup specGroup) {
        if (specGroup.getId() != null) {
            // 修改商品规格组
            int count = groupMapper.updateByPrimaryKey(specGroup);
            if (count != 1)
                throw new LyException(ExceptionEnum.SPEC_GROUP_UPDATE_ERROR);
        } else {
            // 新增商品规格组
            specGroup.setId(null);
            int count = groupMapper.insert(specGroup);
            if (count != 1)
                throw new LyException(ExceptionEnum.SPEC_GROUP_SAVE_ERROR);
        }
    }

    public void deleteGroup(Long id) {
        // 删除商品规格组
        int count = groupMapper.deleteByPrimaryKey(id);
        if (count != 1)
            throw new LyException(ExceptionEnum.SPEC_GROUP_DELETE_ERROR);
    }

    public List<SpecParam> queryParamList(Long gid, Long cid, Boolean searching, Boolean generic) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);

        List<SpecParam> list = paramMapper.select(specParam);

        if (CollectionUtils.isEmpty(list))
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        return list;
    }

    public void saveParam(SpecParam specParam) {
        if (specParam.getName() == null)
            throw new LyException(ExceptionEnum.SPEC_PARAM_SAVE_ERROR);
        specParam.setId(null);
        int count = paramMapper.insert(specParam);
        if (count != 1)
            throw new LyException(ExceptionEnum.SPEC_PARAM_SAVE_ERROR);
    }

    public void updateParam(SpecParam specParam) {
        if (specParam.getName() == null)
            throw new LyException(ExceptionEnum.SPEC_PARAM_UPDATE_ERROR);
        int count = paramMapper.updateByPrimaryKey(specParam);
        if (count != 1)
            throw new LyException(ExceptionEnum.SPEC_PARAM_UPDATE_ERROR);
    }

    public void deleteParam(Long id) {
        int count = paramMapper.deleteByPrimaryKey(id);
        if (count != 1)
            throw new LyException(ExceptionEnum.SPEC_PARAM_DELETE_ERROR);
    }

    /**
     * 根据分类 id 查询规格组及组内参数
     *
     * @param cid
     * @return
     */
    public List<SpecGroup> queryListByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> specGroups = queryGroupByCid(cid);
        // 查询当前分类下的参数
        List<SpecParam> specParams = queryParamList(null, cid, null, null);
        // 先把规格参数变成 map ,map 的 key 是规格组的id, map 的值是组下的所有参数
        Map<Long, List<SpecParam>> map = new HashMap<>();

        for (SpecParam param : specParams) {
            if (!map.containsKey(map.get(param.getGroupId()))) {
                // 这个组 id 在 map 中不存在，新增一个list
                map.put(param.getGroupId(), new ArrayList<>());
            }
            map.get(param.getGroupId()).add(param);
        }

        // 填充 param 到 group 中
        for (SpecGroup specGroup : specGroups) {
            specGroup.setParams(map.get(specGroup.getId()));
        }
        return specGroups;
    }
}
