package com.aoyang.modules.base.service.impl;

import com.aoyang.modules.base.dao.BaseMemberDao;
import com.aoyang.modules.base.entity.BaseMemberEntity;
import com.aoyang.modules.base.service.BaseMemberService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("baseMemberService")
public class BaseMemberServiceImpl extends ServiceImpl<BaseMemberDao, BaseMemberEntity> implements BaseMemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<BaseMemberEntity> page = this.selectPage(
                new Query<BaseMemberEntity>(params).getPage(),
                new EntityWrapper<BaseMemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils listPage(Map<String, Object> params) {
        Page<BaseMemberEntity> page = new Query<BaseMemberEntity>(params).getPage();
        List<BaseMemberEntity> baseMemberEntities = baseMapper.listPage(page, (String) params.get("keyword"));
        return new PageUtils(page.setRecords(baseMemberEntities));
    }

    @Override
    public List<BaseMemberEntity> selectByPatNo(String patNo) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("patNo", patNo);
        return baseMapper.selectByPatNo(param);
    }


}
